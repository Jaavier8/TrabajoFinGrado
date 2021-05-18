import subprocess
import sys, os

from stix2 import Indicator

def cmd(cmd):
    """Executes the command in cmd and exits if it fails"""
    res = subprocess.call(cmd, shell=True)
    if res != 0:
        # logger.error(f'Error: The result was {res} when executing command "{cmd}".')
        print_error(f'[-] Error: The result was {res} when executing command "{cmd}".')
        sys.exit(1)

requests=[]
domains=[]
domains_stix=[]
domains_stix2json=[]

def get_baddomains():
    with open('./bad_domains/baddomain_request.txt') as file:
        for domain in file:
            if domain not in requests:
                requests.append(domain.rstrip('\n'))
    for request in requests:
        cmd(f"{request} -s >> domains.txt")
        with open('domains.txt') as file:
            for line in file:
                domains.append({'domain': line.split(',')[0], 'reason': line.split(',')[4].rstrip('\n')})
        domains.pop(0) #ELIMINAR PRIMER ELEMENTO PORQUE NO ES DOMINIO
        cmd('rm domains.txt')
    return domains

def baddomains2stix(baddomains):
    for domain in baddomains:
        domains_stix.append(Indicator(name="Domain used for malicious activities",
                                        description=domain['reason'],
                                        pattern="[domain:value = '" + domain['domain'] + "']",
                                        pattern_type="stix"))
    for domain in domains_stix:
        domains_stix2json.append({"type" : domain["type"],
                                    "spec_version" : domain["spec_version"],
                                    "id" : domain["id"],
                                    "created" : str(domain["created"]),
                                    "modified" : str(domain["modified"]),
                                    "name" : domain["name"],
                                    "description" : domain["description"],
                                    "pattern" : domain["pattern"],
                                    "pattern_type" : domain["pattern_type"],
                                    "pattern_version" : domain["pattern_version"] ,
                                    "valid_from" : str(domain["valid_from"])})
    return domains_stix2json
