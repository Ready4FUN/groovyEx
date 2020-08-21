@Grab(group='org.apache.httpcomponents', module='httpclient', version='4.5.2')

import groovy.json.*

import org.apache.http.client.methods.*
import org.apache.http.entity.*
import org.apache.http.impl.client.*


// build HTTP GET
String apiKey = "get an https://developer.here.com/"

def url = "https://revgeocode.search.hereapi.com/v1/revgeocode?at=59.9392259%2C29.5342917&lang=en-US&apiKey=${apiKey}"
def get = new HttpGet(url)

get.addHeader("content-type", "application/json")

// execute 

def client = HttpClientBuilder.create().build()
def response = client.execute(get)

int code = response.getStatusLine().getStatusCode()

if(code == 200){
    def bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
    def jsonResponse = bufferedReader.getText()
    println "response: \n" + jsonResponse

    def slurper = new JsonSlurper()
    def resultMap = slurper.parseText(jsonResponse)

    assert resultMap["items"] != null

    println "jsonParse---------------------------------------------" 
    println resultMap["items"][0]["title"]
} else {
    print "error request" + code
}



