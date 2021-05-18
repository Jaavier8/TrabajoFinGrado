import otx_misp
import time
from datetime import datetime

API_KEY = "1867b144115ca34d2e4f99035863f257db611a9662459233334b744dcb984487"

time24 = time.time() - 24*3600

test = otx_misp.get_pulses(API_KEY, from_timestamp=datetime(2021,5,17,10,00,00,00000))
#print (test[0]["indicators"][0])
#print (test[1]["indicators"][0])
print(test)
