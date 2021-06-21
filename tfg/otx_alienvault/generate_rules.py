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

cve = {"CVE-2021-26855" : {"typeofasset" : "cyberthreat_DRM:Server", "conditionasset" : "cibersituational-ontology:name 'Microsoft Exchange Server'"},
        "CVE-2017-8570" : {"typeofasset" : "cyberthreat_DRM:AcquiredSW", "conditionasset" : "cibersituational-ontology:name 'Microsoft Office'"},
        "CVE-2021-26857" : {"typeofasset" : "cyberthreat_DRM:Server", "conditionasset" : "cibersituational-ontology:name 'Microsoft Exchange Server'"},
        "CVE-2021-26858" : {"typeofasset" : "cyberthreat_DRM:Server", "conditionasset" : "cibersituational-ontology:name 'Microsoft Exchange Server'"},
        "CVE-2021-27065" : {"typeofasset" : "cyberthreat_DRM:Server", "conditionasset" : "cibersituational-ontology:name 'Microsoft Exchange Server'"},
        "CVE-2021-27075" : {"typeofasset" : "cyberthreat_DRM:AcquiredSW", "conditionasset" : "cibersituational-ontology:name 'Microsoft Azure'"},
        "CVE-2020-1472" : {"typeofasset" : "cyberthreat_DRM:Server", "conditionasset" : "cibersituational-ontology:name 'Microsoft Server Message'"},
        "CVE-2017-0147" : {"typeofasset" : "cyberthreat_DRM:Server", "conditionasset" : "cibersituational-ontology:name 'Microsoft Server Message'"}}

# for item in cve:
#     cmd(f"curl http://cve.circl.lu/api/cve/{item} >> test_cve/test_{item}.json")

def generate_rules(cvename, campaignid, malwarename):
    cmd(f"curl https://cve.circl.lu/api/cve/{cvename} >> cve.json")
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
    file = open('otx_alienvault/SPINrules_templates/ids/vulnerability_idnumber.txt','r')
    numberofvulnerability = file.read().strip().split('=')[1]
    file.close()
    file = open('otx_alienvault/SPINrules_templates/ids/vulnerability_idnumber.txt','w')
    file.write(f'vulnerability={str(int(numberofvulnerability)+1)}')
    file.close()
    rule = f"""CONSTRUCT {{
    	?newv a cyberthreat_STIX:Vulnerability.
    	?newv cyberthreat_STIX:description '{cvesummary}' .
    	?newv cibersituational-ontology:name '{cvename}' .
    	?newv cyberthreat_STIX:isTargetedBy ?c.
        ?c cyberthreat_STIX:targets ?newv.
    	?newv cyberthreat_STIX:isTargetedBy ?m.
        ?m cyberthreat_STIX:targets ?newv.
    	?a cyberthreat_STIXDRM:has_vulnerability ?newv.
    }}
    WHERE{{
    	?c a cyberthreat_STIX:Campaign.
    	?m a cyberthreat_STIX:Malware.
    	?a a {cve[cvename]["typeofasset"]}.
        ?c cyberthreat_STIX:id '{campaignid}'.
        ?m cibersituational-ontology:name '{malwarename}'.
    	?a {cve[cvename]["conditionasset"]}.
    	BIND(URI(cyberthreat_STIX:Vulnerability{numberofvulnerability}) as ?newv)
    }}"""
    with open(f"otx_alienvault/SPINrules/{cvename}_vulnerability", "w") as f:
        f.write(rule)

def generate_threat(cvss, cvename):
    file = open('otx_alienvault/SPINrules_templates/ids/threat_idnumber.txt','r')
    numberofthreat = file.read().strip().split('=')[1]
    file.close()
    file = open('otx_alienvault/SPINrules_templates/ids/threat_idnumber.txt','w')
    file.write(f'threat={str(int(numberofthreat)+1)}')
    file.close()
    rule = f"""CONSTRUCT {{
    	?newa a cyberthreat_DRM:Threat.
    	?newa cibersituational-ontology:impact {cvss}^^xsd:float .
    	?newa cyberthreat_STIX:exploits ?v.
    	?newa cyberthreat_DRM:threatens ?a.
    	?a cyberthreat_DRM:isExposedTo ?newa.
    }}
    WHERE{{
    	?v a cyberthreat_STIX:Vulnerability.
    	?a a {cve[cvename]["typeofasset"]}.
    	?v cibersituational-ontology:name '{cvename}'.
    	{cve[cvename]["conditionasset"]}.
    	BIND(URI(cyberthreat_DRM:Threat{numberofthreat}) as ?newa)
    }}"""
    with open(f"otx_alienvault/SPINrules/{cvename}_threat", "w") as f:
        f.write(rule)

def generate_attack_pattern(attackpatterns, cvename, malwarename):
    i = 0
    for at in attackpatterns:
        file = open('otx_alienvault/SPINrules_templates/ids/ap_idnumber.txt','r')
        numberofattackpattern = file.read().strip().split('=')[1]
        file.close()
        file = open('otx_alienvault/SPINrules_templates/ids/ap_idnumber.txt','w')
        file.write(f'attackpattern={str(int(numberofattackpattern)+1)}')
        file.close()
        rule = f"""CONSTRUCT {{
          ?newap a cyberthreat_STIX:Attack_Pattern.
          ?newap cibersituational-ontology:name '{at["name"]}'.
          ?newap cyberthreat_STIX:description '{at["summary"]}'.
          ?newap cyberthreat_STIX:uses ?m.
          ?m cyberthreat_STIX:isUsedBy ?newap
          ?newap cyberthreat_STIX:targets ?v .
        }}
        WHERE{{
          ?v a cyberthreat_STIX:Vulnerability.
          ?m a cyberthreat_STIX:Malware.
          ?v cibersituational-ontology:name '{cvename}'.
          ?m cibersituational-ontology:name '{malwarename}'.
          BIND(URI(cyberthreat_STIX:Attack_Pattern{numberofattackpattern}) as ?newap)
        }}"""
        with open(f"otx_alienvault/SPINrules/{cvename}_attackpattern_{i}", "w") as f:
            f.write(rule)
        i = i+1

def generate_course_action(attackpatterns, cvename, malwarename):
    i = 0
    for at in attackpatterns:
        file = open('otx_alienvault/SPINrules_templates/ids/coa_idnumber.txt','r')
        numberofcourseofaction = file.read().strip().split('=')[1]
        file.close()
        file = open('otx_alienvault/SPINrules_templates/ids/coa_idnumber.txt','w')
        file.write(f'courseofaction={str(int(numberofcourseofaction)+1)}')
        file.close()
        rule = f"""CONSTRUCT {{
        	?newca a cyberthreat_STIX:Course_of_Action.
        	?newca cyberthreat_STIX:description '{at["solutions"]}' .
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
        	?v cibersituational-ontology:name '{cvename}'.
            ?m cibersituational-ontology:name '{malwarename}'.
            ?m cibersituational-ontology:name '{at["name"]}'.
        	BIND(URI(cyberthreat_STIX:Vulnerability{numberofcourseofaction}) as ?newap)
        }}"""
        with open(f"otx_alienvault/SPINrules/{cvename}_courseofaction_{i}", "w") as f:
            f.write(rule)
        i = i+1


generate_rules("CVE-2020-1472", "campaign--fa1c59e1-e71c-4d1e-aa9b-22f165b8227f", "Cobalt Strike")
