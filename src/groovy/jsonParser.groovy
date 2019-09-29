// https://groovy-lang.org/json.html
import groovy.json.JsonSlurper

String jsonStr = new File("c:\\Users\\Sergey Tsarevich\\Downloads\\data_film_chats.json").text
def jsonSlurper = new JsonSlurper()
def json = jsonSlurper.parseText(jsonStr)

println json.r.utterances.text