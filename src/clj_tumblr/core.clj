(ns clj-tumblr.core
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [oauth.client :as oauth]))

(defn get-body
  "Get the :body of the response to an http GET"
  [url]
  (:body (http/get url)))


(defn to-clj
  "Convert a JSON response to a Clojure object with keywords"
  [json-resp]
  (json/parse-string json-resp true ))

(defn map-to-get-params
  "Convert a map of options to a string of optional params.  Ex: {:limit 5 :offset 2} becomes &limit=20&offset=10
  Note that since this will get tacked on after the api-key"
  [param-map]
  (reduce str (map #(str "&" (name (key %)) "=" (val %)) param-map)))


(defn api-call
  "Note that this is the base used internally.  You shouldn't ever have to call it.
   This actually does the API call. It takes a map with the following keys:
    :base -- either blog or user
    :ident -- either the username or the name of the blog (blogname.tumblr.com)
    :call -- the API call (e.g. likes,avatar,info etc.)

    :auth -- right now this is just the API key (until I figure out this OAuth business)


    options is an optional map of all those optional params you can tack on to a request
    (e.g. {:limit 5 :offest 10})

    api-call will return a map version of the JSON returned by Tumblr.
  "
  [{:keys [base ident call auth]} & [options] ]
  (let [url (format "http://api.tumblr.com/v2/%s/%s/%s?api_key=%s" base ident call auth)]
  to-clj (get-body (str url (map-to-get-params options)))))










