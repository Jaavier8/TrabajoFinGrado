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
hashes=[]
hashes_stix=[]
hashes_stix2json=[]

def get_sslhash():
    with open('./ssl_hash/sslhash_request.txt') as file:
        for domain in file:
            if domain not in requests:
                requests.append(domain.rstrip('\n'))
    for request in requests:
        cmd(f"{request} -s >> hashes.txt")
        with open('hashes.txt') as file:
            for line in file:
                if ('#' not in line):
                    hashes.append({'hash': line.split(',')[1], 'reason': line.split(',')[2].rstrip('\n')})
        cmd('rm hashes.txt')
    return hashes

def sslhash2stix(sslhash):
    for hash in sslhash:
        hashes_stix.append(Indicator(name="Hash of malicious SSL certificate",
                                        description=hash['reason'],
                                        pattern="[file:hashes.'SHA-1' = '" + hash['hash'] + "']",
                                        pattern_type="stix"))
    for hash in hashes_stix:
        hashes_stix2json.append({"type" : hash["type"],
                                    "spec_version" : hash["spec_version"],
                                    "id" : hash["id"],
                                    "created" : str(hash["created"]),
                                    "modified" : str(hash["modified"]),
                                    "name" : hash["name"],
                                    "description" : hash["description"],
                                    "pattern" : hash["pattern"],
                                    "pattern_type" : hash["pattern_type"],
                                    "pattern_version" : hash["pattern_version"] ,
                                    "valid_from" : str(hash["valid_from"])})
    return hashes_stix2json
