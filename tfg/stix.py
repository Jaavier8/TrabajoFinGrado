from bad_ips.get_badips import badips2stix
from ssl_ips.get_sslips import sslips2stix
from ssl_hash.get_sslhash import sslhash2stix
from bad_domains.get_baddomains import baddomains2stix
from malware_hash.get_malwarehash import malwarehash2stix
from disposable_emails_domains.get_emaildomains import emaildomains2stix

def convert2stix(data, type):
    if type == "baddomains":
        return baddomains2stix(data)
    elif type == "ips":
        return badips2stix(data, "ips")
    elif type == "subnets":
        return badips2stix(data, "subnets")
    elif type == "emaildomains":
        return emaildomains2stix(data)
    elif type == "malwarehash":
        return malwarehash2stix(data)
    elif type == "sslhash":
        return sslhash2stix(data)
    elif type == "sslips":
        return sslips2stix(data)
