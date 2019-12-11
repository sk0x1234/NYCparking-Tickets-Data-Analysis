#!/usr/bin/env python


from matplotlib import pyplot as plt


dev_x = [   "13610", "10210", "25390", "24890" , "10110" , "10010", "10810" , "59990" , "10410" , "11710"   ]
dev_y = [    539100.0 , 467127.0, 324215.0, 274596.0 , 253398.0, 234368.0, 193939.0, 191441.0, 185360.0, 182830.0   ]


plt.bar(dev_x, dev_y );
plt.title('top 10 Street codes vs  Number of Tickets generated');

plt.xlabel('Streetcodes')
plt.ylabel('Number of Tickets')
plt.show();
