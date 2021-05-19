import os
import otx_misp
import time
import stix2
from datetime import datetime

import json

API_KEY = "1867b144115ca34d2e4f99035863f257db611a9662459233334b744dcb984487"

# timenow = time.time()
# time24 = timenow - 24*3600
# print(datetime.utcfromtimestamp(timenow).strftime('%Y-%m-%d %H:%M:%S'))
# print(datetime.utcfromtimestamp(time24).strftime('%Y-%m-%d %H:%M:%S'))
# print(datetime.fromtimestamp(time24).strftime('%Y-%m-%d %H:%M:%S'))

#Cogemos la hora en la que se produjo la última descarga y la actualizamos
def get_upload_datetime():
    if(os.path.isfile(os.getcwd() + "/otx_alienvault/datetime_last_download.txt")):
        with open(os.getcwd() + "/otx_alienvault/datetime_last_download.txt") as file:
            last_datetime = datetime.strptime(file.read().strip().split('=')[1], "%m/%d/%Y, %H:%M:%S")
    else:
        last_datetime = datetime.now()
    with open(os.getcwd() + "/otx_alienvault/datetime_last_download.txt", "w") as file:
        file.write("Last_download=" + datetime.now().strftime("%m/%d/%Y, %H:%M:%S"))
        #file.write("Last_download=05/03/2021, 07:32:51")
    return last_datetime

def get_alienvault_data():
    download_from_datetime = get_upload_datetime()
    data = otx_misp.get_pulses(API_KEY, from_timestamp=download_from_datetime)
    return data

def alienvault_data2stix(data):
    bundles = []
    #Every pulse is a bundle
    for pulse in data:
        bundle_objects = []
        #Creating external references for the campaign
        references = []
        for reference in pulse["references"]:
            references.append(stix2.ExternalReference(source_name="Unknown website name",
                                                        url=reference))

        #Creating campaign
        campaign = stix2.Campaign(name=pulse["name"],
                                    description=pulse["description"],
                                    created=datetime.strptime(pulse["created"], "%Y-%m-%dT%H:%M:%S.%f"),
                                    modified=datetime.strptime(pulse["modified"], "%Y-%m-%dT%H:%M:%S.%f"),
                                    external_references=references,
                                    labels=pulse["tags"])
        bundle_objects.append(campaign)

        #Creating indicators and relationships with campaign
        for indicator in pulse["indicators"]:
            ind = stix2.Indicator(name=indicator["title"],
                                    description=indicator["description"],
                                    created=datetime.strptime(indicator["created"], "%Y-%m-%dT%H:%M:%S"),
                                    pattern="[" + indicator["type"] + ":value = '" + indicator["indicator"] + "']",
                                    pattern_type="stix")
            bundle_objects.append(ind)
            bundle_objects.append(stix2.Relationship(relationship_type="indicates",
                                                    source_ref=ind["id"],
                                                    target_ref=campaign["id"]))
        bundles.append(stix2.Bundle(objects=bundle_objects))
    print(bundles[0])
    print(bundles[1])
    with open('out.txt', 'w') as f:
        print(bundles[0], file=f)




#get_alienvault_data()
alienvault_data2stix(get_alienvault_data())
#
# with open('otx.txt', 'a+') as f:
#     for i in test:
#         f.write(json.dumps(i))
# print (test[0]["indicators"][0])
# print (test[1]["indicators"][0])
