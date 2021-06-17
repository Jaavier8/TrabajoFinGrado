import subprocess
import sys, os

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

for item in cve:
    cmd(f"curl http://cve.circl.lu/api/cve/{item} >> test_cve/test_{item}.json")

def generate_rules(cve):
"""
// Regla para generar una vulnerabilidad y asociarla a Campaign y Malware
CONSTRUCT {
	?newv a cyberthreat_STIX:Vulnerability.
	?newv cyberthreat_STIX:description {summary} .
	?newv cibersituational-ontology:name {name} .
	?newv cyberthreat_STIX:isTargetedBy ?c.
    ?c cyberthreat_STIX:targets ?newv.
	?newv cyberthreat_STIX:isTargetedBy ?m.
    ?m cyberthreat_STIX:targets ?newv.
}
WHERE{
	?c a cyberthreat_STIX:Campaign.
	?m a cyberthreat_STIX:Malware.
    ?c cyberthreat_STIX:id {id}.
    ?m cibersituational-ontology:name {mname}.
}

// Regla para generar una amenaza a través de la vulnerabilidad anterior
CONSTRUCT {
	?newa a cyberthreat_DRM:Threat.
	?newa cibersituational-ontology:impact {cvss}^^xsd:float .
	?newa cyberthreat_STIX:exploits ?v.
}
WHERE{
	?v a cyberthreat_STIX:Vulnerability.
	?v cibersituational-ontology:name {name}.
}

// Regla para generar un Attack Pattern de la vulnerabilidad anterior
CONSTRUCT {
	?newap a cyberthreat_STIX:Attack_Pattern.
    ?newap cibersituational-ontology:name {attackname}.
    ?newap cyberthreat_STIX:description {attackdescription}.
    ?newap cyberthreat_STIX:uses ?m.
    ?m cyberthreat_STIX:isUsedBy ?newap
	?newap cyberthreat_STIX:targets ?v .
}
WHERE{
	?v a cyberthreat_STIX:Vulnerability.
    ?m a cyberthreat_STIX:Malware.
	?v cibersituational-ontology:name {name}.
    ?m cibersituational-ontology:name {mname}.
}

// Regla para generar un Course of Action para el Attack Pattern anterior
CONSTRUCT {
	?newca a cyberthreat_STIX:Course_of_Action.
	?newca cyberthreat_STIX:description {solutions} .
    ?newca cyberthreat_DRM:mitigates ?ap.
    ?newca cyberthreat_DRM:mitigates ?m.
    ?newca cyberthreat_STIX:remediates ?v.
    ?newca cyberthreat_STIX:remediates ?m.
    ?m cibersituational-ontology:isMitigatedBy ?newca.
}
WHERE{
	?v a cyberthreat_STIX:Vulnerability.
    ?m a cyberthreat_STIX:Malware.
    ?ap a cyberthreat_STIX:Attack_Pattern.
	?v cibersituational-ontology:name {name}.
    ?m cibersituational-ontology:name {mname}.
    ?m cibersituational-ontology:name {attackname}.
}
"""
