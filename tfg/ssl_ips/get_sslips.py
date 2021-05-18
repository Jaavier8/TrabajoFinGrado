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
ips=[]
ips_stix=[]
ips_stix2json=[]

def get_sslips():
    with open('./ssl_ips/sslip_request.txt') as file:
        for domain in file:
            if domain not in requests:
                requests.append(domain.rstrip('\n')) #CAMBIAR IP_REQUEST
    for request in requests:
        cmd(f"{request} -s >> ssl_ips.txt")
        with open('ssl_ips.txt') as file:
            for line in file:
                if (line not in ips) and ('#' not in line):
                    ips.append(line.rstrip('\n'))
        cmd('rm ssl_ips.txt')
    return ips

def sslips2stix(sslips):
    for ip in sslips:
        ips_stix.append(Indicator(name="IPs running with a malicious certificate",
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
