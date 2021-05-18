import subprocess
import sys, os

from stix2 import Indicator

def cmd(cmd):
    """Executes the command in cmd and exits if it fails"""
    #res = subprocess.call(cmd.split(' '), shell=True)
    res = subprocess.call(cmd, shell=True)
    if res != 0:
        # logger.error(f'Error: The result was {res} when executing command "{cmd}".')
        print_error(f'[-] Error: The result was {res} when executing command "{cmd}".')
        sys.exit(1)

requests=[]
ips=[]
subnets=[]
ips_stix=[]
ips_stix2json=[]
subnets_stix=[]
subnets_stix2json=[]

def get_badips():
    """Introduce the IPs and subnets in two differents arrays"""
    with open('./bad_ips/badip_request.txt') as file:
        for domain in file:
            if domain not in requests:
                requests.append(domain.rstrip('\n'))
    for request in requests:
        cmd(f"{request} -s >> bad_ips.txt")
        with open('bad_ips.txt') as file:
            for line in file:
                if (line not in ips) and ('#' not in line):
                    if '/' in line:
                        subnets.append(line.rstrip('\n'))
                    else:
                        ips.append(line.rstrip('\n'))
        cmd('rm bad_ips.txt')
    return {'ips': ips, 'subnets': subnets}

def badips2stix(element, type):
    if type == "ips":
        for ip in element:
            ips_stix.append(Indicator(name="IP used for malicious activities",
                                        pattern="[ipv4-addr:value = '" + ip + "']",
                                        pattern_type="stix"))
        for ip in ips_stix:
            ips_stix2json.append({"type" : ip["type"],
                                    "spec_version" : ip["spec_version"],
                                    "id" : ip["id"],
                                    "created" : str(ip["created"]),
                                    "modified" : str(ip["modified"]),
                                    "name" : ip["name"],
                                    "pattern" : ip["pattern"],
                                    "pattern_type" : ip["pattern_type"],
                                    "pattern_version" : ip["pattern_version"] ,
                                    "valid_from" : str(ip["valid_from"])})
        return ips_stix2json
    elif type == "subnets":
        for subnet in element:
            subnets_stix.append(Indicator(name="Subnet used for malicious activities",
                                        pattern="[ipv4-addr:value = '" + subnet + "']",
                                        pattern_type="stix"))
        for subnet in subnets_stix:
            subnets_stix2json.append({"type" : subnet["type"],
                                    "spec_version" : subnet["spec_version"],
                                    "id" : subnet["id"],
                                    "created" : str(subnet["created"]),
                                    "modified" : str(subnet["modified"]),
                                    "name" : subnet["name"],
                                    "pattern" : subnet["pattern"],
                                    "pattern_type" : subnet["pattern_type"],
                                    "pattern_version" : subnet["pattern_version"] ,
                                    "valid_from" : str(subnet["valid_from"])})
        return subnets_stix2json
