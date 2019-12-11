#!/usr/bin/env python

import numpy as np
from matplotlib import pyplot as plt


Years = [2015,2016,2017]
dev_x =  [   "JAN" , "FEB" , "MAR" ,  "APR" , "MAY",  "JUN",     "JUL",    "AUG",   "SEP",   "OCT",   "NOV" ,  "DEC" ]
dev_y15 = [  1392188 ,731399 ,958010, 918486 , 985490 , 1226183 ,  884848, 902670 ,  939402,  1097019 , 935386, 767106  ]
dev_y16 = [  815191 ,840650 ,1014146, 900957, 880425,  678051,  700814,  801585,  960909, 969581,  899626,  779024   ]

dev_y17 = [  877484, 827092 ,964928 ,888538, 1020369,  852243, 843114,  820355 , 833428 , 851333 , 823398 , 800404  ]

xpos = np.arange(len(dev_x))
#plt.plot(dev_x, dev_y15,label="2015",color="#d96459" )
#plt.plot(dev_x, dev_y16,label="2016" ,color="#588c7e" )
#plt.plot(dev_x, dev_y17,label="2017" ,color="#A4C639" )

plt.bar(xpos-0.25, dev_y15, width=0.2   ,label="2015",color="#d96459" )
plt.bar(xpos+0.0, dev_y16,  width=0.2   ,label="2016" ,color="#588c7e" )
plt.bar(xpos+0.25, dev_y17, width=0.2   ,label="2017" ,color="#A4C639" )

plt.title('Tickets generated in Each Month in 3 Year');


plt.legend(Years,loc=2)
plt.xlabel('Time')
plt.ylabel('Number of Tickets')
plt.show();
