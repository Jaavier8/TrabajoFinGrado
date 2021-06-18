import subprocess
import sys, os
import json

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

cve = ["CVE-2021-26855", "CVE-2017-8570", "CVE-2021-26857", "CVE-2021-26858", "CVE-2019-11510",
        "CVE-2021-27065", "CVE-2021-27075", "CVE-2017-1000253", "CVE-2020-1472", "CVE-2017-0147"]

# for item in cve:
#     cmd(f"curl http://cve.circl.lu/api/cve/{item} >> test_cve/test_{item}.json")
# cve, campaignId, malwareName
def generate_rules(cvename, campaignid, malwarename):
    cmd(f"curl http://cve.circl.lu/api/cve/{cvename} >> cve.json")
    with open('cve.json') as file:
        data = json.load(file)
    cvesummary = data["summary"]
    cvss = data["cvss"]
    attackpatterns = data["capec"]
    cmd("rm cve.json")
    generate_vulnerability(cvesummary, cvename, campaignid, malwarename)
    generate_threat(cvss, cvename)
    if(len(attackpatterns) >= 0):
        generate_attack_pattern(attackpatterns, cvename, malwarename)
        generate_course_action(attackpatterns, cvename, malwarename)
    # with open("otx_alienvault/SPINrules_templates/threat.txt") as f:
    #     print()
        # lineas = f.readlines();
        # for linea in lineas:
        #     print(linea)
        #     if("cvss" in linea):
        #         line = linea.replace('cvss','hola')
        #         print(line)

def generate_vulnerability(cvesummary, cvename, campaignid, malwarename):
    rule = f"""CONSTRUCT{{
    	?newv a cyberthreat_STIX:Vulnerability.
    	?newv cyberthreat_STIX:description {cvesummary} .
    	?newv cibersituational-ontology:name {cvename} .
    	?newv cyberthreat_STIX:isTargetedBy ?c.
        ?c cyberthreat_STIX:targets ?newv.
    	?newv cyberthreat_STIX:isTargetedBy ?m.
        ?m cyberthreat_STIX:targets ?newv.
    }}
    WHERE{{
    	?c a cyberthreat_STIX:Campaign.
    	?m a cyberthreat_STIX:Malware.
        ?c cyberthreat_STIX:id {campaignid}.
        ?m cibersituational-ontology:name {malwarename}.
    }}"""
    with open(f"otx_alienvault/SPINrules/{cvename}_vulnerability", "w") as f:
        f.write(rule)

def generate_threat(cvss, cvename):
    rule = f"""CONSTRUCT{{
    	?newa a cyberthreat_DRM:Threat.
    	?newa cibersituational-ontology:impact {cvss}^^xsd:float .
    	?newa cyberthreat_STIX:exploits ?v.
    }}
    WHERE{{
    	?v a cyberthreat_STIX:Vulnerability.
    	?v cibersituational-ontology:name {cvename}.
    }}"""
    with open(f"otx_alienvault/SPINrules/{cvename}_threat", "w") as f:
        f.write(rule)

def generate_attack_pattern(attackpatterns, cvename, malwarename):
    i = 0
    for at in attackpatterns:
        rule = f"""CONSTRUCT{{
        	?newap a cyberthreat_STIX:Attack_Pattern.
            ?newap cibersituational-ontology:name {at["name"]}.
            ?newap cyberthreat_STIX:description {at["summary"]}.
            ?newap cyberthreat_STIX:uses ?m.
            ?m cyberthreat_STIX:isUsedBy ?newap
        	?newap cyberthreat_STIX:targets ?v .
        }}
        WHERE{{
        	?v a cyberthreat_STIX:Vulnerability.
            ?m a cyberthreat_STIX:Malware.
        	?v cibersituational-ontology:name {cvename}.
            ?m cibersituational-ontology:name {malwarename}.
        }}"""
        with open(f"otx_alienvault/SPINrules/{cvename}_attackpattern_{i}", "w") as f:
            f.write(rule)
        i = i+1

def generate_course_action(attackpatterns, cvename, malwarename):
    i = 0
    for at in attackpatterns:
        rule = f"""CONSTRUCT{{
        	?newca a cyberthreat_STIX:Course_of_Action.
        	?newca cyberthreat_STIX:description {at["solutions"]} .
            ?newca cyberthreat_DRM:mitigates ?ap.
            ?newca cyberthreat_DRM:mitigates ?m.
            ?newca cyberthreat_STIX:remediates ?v.
            ?newca cyberthreat_STIX:remediates ?m.
            ?m cibersituational-ontology:isMitigatedBy ?newca.
        }}
        WHERE{{
        	?v a cyberthreat_STIX:Vulnerability.
            ?m a cyberthreat_STIX:Malware.
            ?ap a cyberthreat_STIX:Attack_Pattern.
        	?v cibersituational-ontology:name {cvename}.
            ?m cibersituational-ontology:name {malwarename}.
            ?m cibersituational-ontology:name {at["name"]}.
        }}"""
        with open(f"otx_alienvault/SPINrules/{cvename}_courseofaction_{i}", "w") as f:
            f.write(rule)
        i = i+1


generate_rules("CVE-2017-0147", "campaign--b1509899-4f95-4b1e-8fd5-76cbd80baf9a", "Matanbuchus")
