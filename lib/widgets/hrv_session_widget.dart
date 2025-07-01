import 'package:flutter/material.dart';
import 'dart:math';
import '../hrv_session_service.dart';

class HRVSessionWidget extends StatefulWidget {
  final HRVSessionService sessionService;
  final bool isConnected;
  final VoidCallback? onStartSession;

  const HRVSessionWidget({
    super.key,
    required this.sessionService,
    required this.isConnected,
    this.onStartSession,
  });

  @override
  State<HRVSessionWidget> createState() => _HRVSessionWidgetState();
}

class _HRVSessionWidgetState extends State<HRVSessionWidget> 
    with TickerProviderStateMixin {
  
  late AnimationController _pulseController;
  late Animation<double> _pulseAnimation;
  
  @override
  void initState() {
    super.initState();
    _pulseController = AnimationController(
      duration: const Duration(milliseconds: 1000),
      vsync: this,
    );
    _pulseAnimation = Tween<double>(
      begin: 0.8,
      end: 1.2,
    ).animate(CurvedAnimation(
      parent: _pulseController,
      curve: Curves.easeInOut,
    ));
  }

  @override
  void dispose() {
    _pulseController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8.0),
      elevation: 4,
      child: StreamBuilder<HRVSessionData?>(
        stream: widget.sessionService.sessionStream,
        builder: (context, snapshot) {
          final session = snapshot.data;
          return _buildSessionContent(context, session);
        },
      ),
    );
  }

  Widget _buildSessionContent(BuildContext context, HRVSessionData? session) {
    if (session == null) {
      return _buildIdleState(context);
    }

    switch (session.state) {
      case HRVSessionState.recording:
        _startPulseAnimation();
        return _buildRecordingState(context, session);
      case HRVSessionState.paused:
        _stopPulseAnimation();
        return _buildPausedState(context, session);
      case HRVSessionState.completed:
        _stopPulseAnimation();
        return _buildCompletedState(context, session);
      case HRVSessionState.error:
        _stopPulseAnimation();
        return _buildErrorState(context);
      default:
        _stopPulseAnimation();
        return _buildIdleState(context);
    }
  }

  Widget _buildIdleState(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Header
          Row(
            children: [
              Icon(
                Icons.favorite,
                color: widget.isConnected ? Colors.red : Colors.grey,
                size: 24,
              ),
              const SizedBox(width: 8),
              Text(
                'HRV Session',
                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                  fontWeight: FontWeight.bold,
                ),
              ),
            ],
          ),
          const SizedBox(height: 16),
          
          // Connection status
          if (!widget.isConnected) ...[
            const Icon(
              Icons.bluetooth_disabled,
              size: 48,
              color: Colors.grey,
            ),
            const SizedBox(height: 8),
            Text(
              'Connect device to start HRV measurement',
              style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                color: Colors.grey[600],
              ),
              textAlign: TextAlign.center,
            ),
          ] else ...[
            Container(
              width: 80,
              height: 80,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.red.withValues(alpha: 0.1),
                border: Border.all(color: Colors.red, width: 2),
              ),
              child: const Icon(
                Icons.favorite,
                size: 40,
                color: Colors.red,
              ),
            ),
            const SizedBox(height: 12),
            Text(
              'Ready to measure HRV',
              style: Theme.of(context).textTheme.titleSmall?.copyWith(
                fontWeight: FontWeight.w600,
                color: Colors.red,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Minimum 1 minute session required\nfor accurate HRV analysis',
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: Colors.grey[600],
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 16),
            
            // Start button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: _canStartSession() ? _startSession : null,
                icon: const Icon(Icons.play_arrow),
                label: const Text('Start HRV Session'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
              ),
            ),
          ],
        ],
      ),
    );
  }

  Widget _buildRecordingState(BuildContext context, HRVSessionData session) {
    final progress = session.duration.inSeconds / 60.0; // Progress towards 1 minute
    final progressClamped = min(1.0, progress);
    
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Header with status
          Row(
            children: [
              AnimatedBuilder(
                animation: _pulseAnimation,
                builder: (context, child) {
                  return Transform.scale(
                    scale: _pulseAnimation.value,
                    child: const Icon(
                      Icons.favorite,
                      color: Colors.red,
                      size: 24,
                    ),
                  );
                },
              ),
              const SizedBox(width: 8),
              Text(
                'Recording HRV',
                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                  fontWeight: FontWeight.bold,
                  color: Colors.red,
                ),
              ),
              const Spacer(),
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                decoration: BoxDecoration(
                  color: Colors.red.withValues(alpha: 0.1),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Text(
                  'LIVE',
                  style: Theme.of(context).textTheme.labelSmall?.copyWith(
                    color: Colors.red,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(height: 20),
          
          // Circular progress indicator
          Stack(
            alignment: Alignment.center,
            children: [
              SizedBox(
                width: 120,
                height: 120,
                child: CircularProgressIndicator(
                  value: progressClamped,
                  strokeWidth: 8,
                  backgroundColor: Colors.grey.withValues(alpha: 0.2),
                  valueColor: AlwaysStoppedAnimation<Color>(
                    progressClamped >= 1.0 ? Colors.green : Colors.red,
                  ),
                ),
              ),
              Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    _formatDuration(session.duration),
                    style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                      fontWeight: FontWeight.bold,
                      color: progressClamped >= 1.0 ? Colors.green : Colors.red,
                    ),
                  ),
                  Text(
                    'of 1:00 min',
                    style: Theme.of(context).textTheme.bodySmall?.copyWith(
                      color: Colors.grey[600],
                    ),
                  ),
                ],
              ),
            ],
          ),
          const SizedBox(height: 20),
          
          // Session stats
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              _buildStatItem(
                context,
                'RR Intervals',
                session.totalRRIntervals.toString(),
                Icons.timeline,
              ),
              _buildStatItem(
                context,
                'Quality',
                session.quality != null ? '${session.quality!.round()}%' : '-%',
                Icons.signal_cellular_alt,
              ),
            ],
          ),
          const SizedBox(height: 20),
          
          // Control buttons
          Row(
            children: [
              Expanded(
                child: OutlinedButton.icon(
                  onPressed: _pauseSession,
                  icon: const Icon(Icons.pause),
                  label: const Text('Pause'),
                ),
              ),
              const SizedBox(width: 12),
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: _stopSession,
                  icon: const Icon(Icons.stop),
                  label: const Text('Stop'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orange,
                    foregroundColor: Colors.white,
                  ),
                ),
              ),
            ],
          ),
          
          // Minimum duration warning
          if (progressClamped < 1.0) ...[
            const SizedBox(height: 12),
            Container(
              padding: const EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.amber.withValues(alpha: 0.1),
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.amber, width: 1),
              ),
              child: Row(
                children: [
                  const Icon(Icons.info_outline, color: Colors.amber, size: 16),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      'Continue for ${60 - session.duration.inSeconds}s more for accurate results',
                      style: Theme.of(context).textTheme.bodySmall?.copyWith(
                        color: Colors.amber[800],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ],
      ),
    );
  }

  Widget _buildPausedState(BuildContext context, HRVSessionData session) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Header
          Row(
            children: [
              const Icon(Icons.pause_circle, color: Colors.orange, size: 24),
              const SizedBox(width: 8),
              Text(
                'Session Paused',
                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                  fontWeight: FontWeight.bold,
                  color: Colors.orange,
                ),
              ),
            ],
          ),
          const SizedBox(height: 16),
          
          // Current stats
          Text(
            'Duration: ${_formatDuration(session.duration)}',
            style: Theme.of(context).textTheme.titleSmall,
          ),
          Text(
            'RR Intervals: ${session.totalRRIntervals}',
            style: Theme.of(context).textTheme.bodyMedium,
          ),
          const SizedBox(height: 16),
          
          // Control buttons
          Row(
            children: [
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: _resumeSession,
                  icon: const Icon(Icons.play_arrow),
                  label: const Text('Resume'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green,
                    foregroundColor: Colors.white,
                  ),
                ),
              ),
              const SizedBox(width: 12),
              Expanded(
                child: OutlinedButton.icon(
                  onPressed: _stopSession,
                  icon: const Icon(Icons.stop),
                  label: const Text('Stop'),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildCompletedState(BuildContext context, HRVSessionData session) {
    final metrics = session.metrics;
    
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Header
          Row(
            children: [
              const Icon(Icons.check_circle, color: Colors.green, size: 24),
              const SizedBox(width: 8),
              Text(
                'Session Complete',
                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                  fontWeight: FontWeight.bold,
                  color: Colors.green,
                ),
              ),
            ],
          ),
          const SizedBox(height: 16),
          
          // Session summary
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.green.withValues(alpha: 0.1),
              borderRadius: BorderRadius.circular(8),
            ),
            child: Column(
              children: [
                Text(
                  'Duration: ${_formatDuration(session.duration)}',
                  style: Theme.of(context).textTheme.titleSmall?.copyWith(
                    fontWeight: FontWeight.w600,
                  ),
                ),
                Text(
                  'Quality: ${session.quality?.round() ?? 0}%',
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
              ],
            ),
          ),
          
          if (metrics != null) ...[
            const SizedBox(height: 16),
            
            // HRV Metrics
            Text(
              'HRV Analysis',
              style: Theme.of(context).textTheme.titleSmall?.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 8),
            
            // Metrics grid
            GridView.count(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              crossAxisCount: 2,
              childAspectRatio: 2.5,
              mainAxisSpacing: 8,
              crossAxisSpacing: 8,
              children: [
                _buildMetricCard(context, 'RMSSD', '${metrics.rmssd.round()}ms', Colors.blue),
                _buildMetricCard(context, 'SDNN', '${metrics.sdnn.round()}ms', Colors.purple),
                _buildMetricCard(context, 'pNN50', '${metrics.pnn50.round()}%', Colors.orange),
                _buildMetricCard(context, 'Mean HR', '${metrics.meanHR.round()} BPM', Colors.red),
              ],
            ),
          ],
          
          const SizedBox(height: 16),
          
          // New session button
          SizedBox(
            width: double.infinity,
            child: ElevatedButton.icon(
              onPressed: _newSession,
              icon: const Icon(Icons.refresh),
              label: const Text('New Session'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.blue,
                foregroundColor: Colors.white,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildErrorState(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Icon(Icons.error, color: Colors.red, size: 48),
          const SizedBox(height: 16),
          Text(
            'Session Error',
            style: Theme.of(context).textTheme.titleMedium?.copyWith(
              fontWeight: FontWeight.bold,
              color: Colors.red,
            ),
          ),
          const SizedBox(height: 8),
          Text(
            'There was an error during the HRV session. Please try again.',
            style: Theme.of(context).textTheme.bodyMedium,
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16),
          ElevatedButton.icon(
            onPressed: _newSession,
            icon: const Icon(Icons.refresh),
            label: const Text('Try Again'),
          ),
        ],
      ),
    );
  }

  Widget _buildStatItem(BuildContext context, String label, String value, IconData icon) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Icon(icon, size: 20, color: Colors.grey[600]),
        const SizedBox(height: 4),
        Text(
          value,
          style: Theme.of(context).textTheme.titleSmall?.copyWith(
            fontWeight: FontWeight.bold,
          ),
        ),
        Text(
          label,
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
            color: Colors.grey[600],
          ),
        ),
      ],
    );
  }

  Widget _buildMetricCard(BuildContext context, String label, String value, Color color) {
    return Container(
      padding: const EdgeInsets.all(8),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.1),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: color.withValues(alpha: 0.3)),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            value,
            style: Theme.of(context).textTheme.titleSmall?.copyWith(
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
          Text(
            label,
            style: Theme.of(context).textTheme.bodySmall?.copyWith(
              color: color.withValues(alpha: 0.8),
            ),
          ),
        ],
      ),
    );
  }

  String _formatDuration(Duration duration) {
    final minutes = duration.inMinutes;
    final seconds = duration.inSeconds % 60;
    return '${minutes.toString().padLeft(1, '0')}:${seconds.toString().padLeft(2, '0')}';
  }

  bool _canStartSession() {
    return widget.isConnected && widget.sessionService.canStartSession;
  }

  void _startSession() {
    // Call the parent callback to start session
    if (_canStartSession() && widget.onStartSession != null) {
      widget.onStartSession!();
    }
  }

  void _pauseSession() {
    widget.sessionService.pauseSession();
  }

  void _resumeSession() {
    widget.sessionService.resumeSession();
  }

  void _stopSession() {
    widget.sessionService.stopSession();
  }

  void _newSession() {
    widget.sessionService.clearSession();
  }

  void _startPulseAnimation() {
    if (!_pulseController.isAnimating) {
      _pulseController.repeat(reverse: true);
    }
  }

  void _stopPulseAnimation() {
    _pulseController.stop();
    _pulseController.reset();
  }
}
