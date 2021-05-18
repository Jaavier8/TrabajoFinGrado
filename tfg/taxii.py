import libtaxii as t
import libtaxii.messages_11 as tm11
import libtaxii.clients as tc
from libtaxii.common import generate_message_id
from libtaxii.constants import *
from dateutil.tz import tzutc

client = tc.HttpClient()
client.set_auth_type(tc.HttpClient.AUTH_NONE)
client.set_use_https(False)

discovery_request = tm11.DiscoveryRequest(generate_message_id())
discovery_xml = discovery_request.to_xml(pretty_print=True)

http_resp = client.call_taxii_service2('hailataxii.com', '/taxii-data', VID_TAXII_XML_11, discovery_xml)
taxii_message = t.get_message_from_http_response(http_resp, discovery_request.message_id)
print(taxii_message.to_json())
