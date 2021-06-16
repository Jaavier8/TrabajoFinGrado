#!/usr/bin/python3
import subprocess
import sys, os
import json
import requests

from bad_ips.get_badips import get_badips
from ssl_ips.get_sslips import get_sslips
from ssl_hash.get_sslhash import get_sslhash
from bad_domains.get_baddomains import get_baddomains
from malware_hash.get_malwarehash import get_malwarehash
from disposable_emails_domains.get_emaildomains import get_emaildomains

from stix import convert2stix

################################

def print_ok(text):
    OKGREEN = '\033[92m'
    ENDC = '\033[0m'
    print(OKGREEN + text + ENDC)

def print_error(text):
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    print(FAIL + text + ENDC)

def print_result(text):
    OKBLUE = '\033[94m'
    ENDC = '\033[0m'
    print(OKBLUE + text + ENDC)

def cmd(cmd):
    """Executes the command in cmd and exits if it fails"""
    res = subprocess.call(cmd, shell=True)
    if res != 0:
        # logger.error(f'Error: The result was {res} when executing command "{cmd}".')
        print_error(f'[-] Error: The result was {res} when executing command "{cmd}".')
        sys.exit(1)

################################

#BAD IPS
print('[*] Downloading bad IPs')
bad_ips = get_badips()
print_ok('[+] IPs downloaded')
print_result('Number of bad IPs:' + str(len(bad_ips['ips'])))
print_result('Number of bad subnets:' + str(len(bad_ips['subnets'])))
print('\n')

print('[*] Converting to stix objects')
bad_ips_stix = convert2stix(bad_ips['ips'],"ips")
bad_subnets_stix = convert2stix(bad_ips['subnets'],"subnets")
print_ok('[+] Conversion completed')
print_result('Number of bad IPs:' + str(len(bad_ips_stix)))
print_result('Number of bad subnets:' + str(len(bad_subnets_stix)))
print('\n')

#SSL IPS
print('[*] Downloading IPs with malicious certificate (SSL)')
ssl_ips = get_sslips()
print_ok('[+] IPs downloaded')
print_result('Number of malicious IPs:' + str(len(ssl_ips)))
print('\n')

print('[*] Converting to stix objects')
ssl_ips_stix = convert2stix(ssl_ips,"sslips")
print_ok('[+] Conversion completed')
print_result('Number of malicious IPs:' + str(len(ssl_ips_stix)))
print('\n')

#SSL CERTIFICATES HASHES
print('[*] Downloading hashes of malicious certificates (SSL)')
ssl_hash = get_sslhash()
print_ok('[+] Hashes downloaded')
print_result('Number of hashes:' + str(len(ssl_hash)))
print('\n')

print('[*] Converting to stix objects')
ssl_hash_stix = convert2stix(ssl_hash,"sslhash")
print_ok('[+] Conversion completed')
print_result('Number of hashes:' + str(len(ssl_hash_stix)))
print('\n')

#BAD DOMAINS
print('[*] Downloading bad domains')
bad_domains = get_baddomains()
print_ok('[+] Domains downloaded')
print_result('Number of bad domains:' + str(len(bad_domains)))
print('\n')

print('[*] Converting to stix objects')
bad_domains_stix = convert2stix(bad_domains,"baddomains")
print_ok('[+] Conversion completed')
print_result('Number of bad domains:' + str(len(bad_domains_stix)))
print('\n')

#MALWARE HASHES
print('[*] Downloading malware hashes')
malware_hash = get_malwarehash()
print_ok('[+] Hashes downloaded')
print_result('Number of malware hashes:' + str(len(malware_hash)))
print('\n')

print('[*] Converting to stix objects')
malware_hash_stix = convert2stix(malware_hash,"malwarehash")
print_ok('[+] Conversion completed')
print_result('Number of malware hashes:' + str(len(malware_hash_stix)))
print('\n')

#DISPOSABLE EMAIL DOMAINS
print('[*] Downloading disposable email domains')
email_domains = get_emaildomains()
print_ok('[+] Domains downloaded')
print_result('Number of disposable domains:' + str(len(email_domains)))
print('\n')

print('[*] Converting to stix objects')
email_domains_stix = convert2stix(email_domains,"emaildomains")
print_ok('[+] Conversion completed')
print_result('Number of disposable domains:' + str(len(email_domains_stix)))
print('\n')
print(type(email_domains_stix[0]))
print(type(json.dumps(email_domains_stix[0])))

print(bad_ips_stix[0])
print(bad_subnets_stix[0])
print(ssl_ips_stix[0])
print(ssl_hash_stix[0])
print(bad_domains_stix[0])
print(malware_hash_stix[0])
print(malware_hash_stix[1])
print(malware_hash_stix[2])
print(email_domains_stix[0])

##################################
#########SEND TO DATABASE#########
##################################
# print('[*] Sending data to a database')
# headers = {'Content-type':'application/json', 'Accept':'application/json'}
# for indicator in email_domains_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
# for indicator in malware_hash_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
# for indicator in bad_domains_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
# for indicator in ssl_hash_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
# for indicator in ssl_ips_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
# for indicator in bad_ips_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
# for indicator in bad_subnets_stix:
#     resp = requests.post('http://localhost:8080/TFG/rest/indicator', json=indicator, headers=headers)
