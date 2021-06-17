import subprocess
import os
import otx_misp
import time
import stix2
import requests
from datetime import datetime

import json

API_KEY = "1867b144115ca34d2e4f99035863f257db611a9662459233334b744dcb984487"

# timenow = time.time()
# time24 = timenow - 24*3600
# print(datetime.utcfromtimestamp(timenow).strftime('%Y-%m-%d %H:%M:%S'))
# print(datetime.utcfromtimestamp(time24).strftime('%Y-%m-%d %H:%M:%S'))
# print(datetime.fromtimestamp(time24).strftime('%Y-%m-%d %H:%M:%S'))

def cmd(cmd):
    """Executes the command in cmd and exits if it fails"""
    #res = subprocess.call(cmd.split(' '), shell=True)
    res = subprocess.call(cmd, shell=True)
    if res != 0:
        # logger.error(f'Error: The result was {res} when executing command "{cmd}".')
        print_error(f'[-] Error: The result was {res} when executing command "{cmd}".')
        sys.exit(1)

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
    #print(data)
    return data

def alienvault_data2stix(data):
    bundles = []
    #Every pulse is a bundle
    for pulse in data:
        bundle_objects = []
        malware_objects = []
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

        #Creating malware
        for malware_family in pulse["malware_families"]:
            malware = stix2.Malware(name=malware_family,
                                    is_family=True)

            bundle_objects.append(malware)
            malware_objects.append(malware)

        #Creating indicators and relationships with campaign and Malware
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
            for malware in malware_objects:
                bundle_objects.append(stix2.Relationship(relationship_type="indicates",
                                                        source_ref=ind["id"],
                                                        target_ref=malware["id"]))

        #List of bundles
        bundles.append(stix2.Bundle(objects=bundle_objects))
    print(len(bundles))
    aux=1
    for bundle in bundles:
        objects = []
        for object in bundle["objects"]:
            if object["type"] == "campaign":
                references = []
                for reference in object["external_references"]:
                    references.append({"source_name" : reference["source_name"], "url" : reference["url"]})
                objects.append({"type" : object["type"],
                                "spec_version" : object["spec_version"],
                                "id" : object["id"],
                                "created" : str(object["created"]),
                                "modified" : str(object["modified"]),
                                "name" : object["name"],
                                "description" : object["description"],
                                "external_references" : references,
                                "labels" : object["labels"]
                                })
            elif object["type"] == "malware":
                objects.append({"type" : object["type"],
                                "spec_version" : object["spec_version"],
                                "id" : object["id"],
                                "created" : str(object["created"]),
                                "modified" : str(object["modified"]),
                                "name" : object["name"],
                                "is_family" : object["is_family"]
                                })
            elif object["type"] == "indicator":
                objects.append({"type" : object["type"],
                                "spec_version" : object["spec_version"],
                                "id" : object["id"],
                                "created" : str(object["created"]),
                                "modified" : str(object["modified"]),
                                "name" : object["name"],
                                "description" : object["description"],
                                "pattern" : object["pattern"],
                                "pattern_type" : object["pattern_type"]
                                })
            elif object["type"] == "relationship":
                objects.append({"type" : object["type"],
                                "spec_version" : object["spec_version"],
                                "id" : object["id"],
                                "created" : str(object["created"]),
                                "modified" : str(object["modified"]),
                                "relationship_type" : object["relationship_type"],
                                "source_ref" : object["source_ref"],
                                "target_ref" : object["target_ref"]
                                })
        # print(bundle["objects"])
        print(type(objects))
        bundle2send = {"type": bundle["type"], "id": bundle["id"], "objects": objects}
        data2send = json.dumps(bundle2send)
        #print(data2send)
        if(aux == 1):
            with open('data.json', 'w') as file:
                json.dump(data2send, file)
            aux = 2
        # with open('bundle.json', 'w') as f:
        #     print(bundle, file=f)
        # with open('bundle.json') as f:
        #     bundle = json.dumps(json.load(f))
            # print(type(bundle))
            # resp = requests.post('http://localhost:8080/TFG/rest/bundle', data=bundle)
            # print(resp)
        headers = {'Content-type':'application/json', 'Accept':'application/json'}
        #resp = requests.post('http://localhost:8080/TFG/rest/bundle', data=data2send, headers=headers)
        #print(resp)
        # cmd('rm bundle.json')






#get_alienvault_data()
alienvault_data2stix(get_alienvault_data())
#
# with open('otx.txt', 'a+') as f:
#     for i in test:
#         f.write(json.dumps(i))
# print (test[0]["indicators"][0])
# print (test[1]["indicators"][0])
