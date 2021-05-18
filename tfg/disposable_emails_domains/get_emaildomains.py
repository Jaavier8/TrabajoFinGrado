import subprocess
import sys, os

from disposable_email_domains import blocklist #Contains a list of disposable email domains

from stix2 import Indicator

emaildomains_stix=[]
emaildomains_stix2json=[]

def get_emaildomains():
    return list(blocklist)

def emaildomains2stix(emaildomains):
    for domain in emaildomains:
        emaildomains_stix.append(Indicator(name="Disposable email domain used for malicious activities",
                                            pattern="[emaildomain:value = '" + domain + "']",
                                            pattern_type="stix"))
    for domain in emaildomains_stix:
        emaildomains_stix2json.append({"type" : domain["type"],
                                    "spec_version" : domain["spec_version"],
                                    "id" : domain["id"],
                                    "created" : str(domain["created"]),
                                    "modified" : str(domain["modified"]),
                                    "name" : domain["name"],
                                    "pattern" : domain["pattern"],
                                    "pattern_type" : domain["pattern_type"],
                                    "pattern_version" : domain["pattern_version"] ,
                                    "valid_from" : str(domain["valid_from"])})
    return emaildomains_stix2json
