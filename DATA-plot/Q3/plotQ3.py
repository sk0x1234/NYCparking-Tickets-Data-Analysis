#!/usr/bin/env python


from matplotlib import pyplot as plt


dev_x = [   "21", "38", "36", "14" , "37"  ]
dev_y = [    4691045.0,3624601.0,3493323.0,2757562.0,2079282.0   ]


plt.barh(dev_x, dev_y , height=0.3 );
plt.title('top 5 Types of violations');

plt.ylabel('Violaton code')
plt.xlabel('Number of Tickets')
plt.show();

