2.14. Sleep Data Request 0x31
Data Bits        APP Requests Device Sleep Data        Device Replies to APP        Function Description
Command Bit        0x31        0x31 or 0x32        Request sleep data
                0x**        UTC time or packet sequence number: highest 8 bits
                0x**        UTC time or packet sequence number: second highest 8 bits
                0x**        UTC time or packet sequence number: second lowest 8 bits
                0x**        UTC time or packet sequence number: lowest 8 bits
                0x**        ACT-0: 5-minute activity index
                0x**        ACT-1: 5-minute activity index
                0x**        ACT-2: 5-minute activity index
                0x**        ACT-n: 5-minute activity index
Notes:
Each 1 byte of activity index represents 5 minutes of sleep status.
UTC is the UTC time of the first activity index. From the second packet, the packet sequence number replaces the UTC data bits for transmission. If there is no data or the data has been sent completely, 0x32 will be replied to the APP to inform the APP that the data has ended or there is no data.
Activity index explanation:
>20 not sleeping
<20 light sleep
Three consecutive 0s indicate deep sleep
The APP needs to calculate the duration of deep sleep, light sleep, and wakefulness based on the data.