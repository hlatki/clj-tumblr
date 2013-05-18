(ns clj-tumblr.core
    (:require [clj-http.client :as http]
              [cheshire.core :as json]
              [oauth.client :as oauth]))








(def base-url "http://api.tumblr.com/v2/blog/")


(defn api-url
  "Base URL for interacting with a particular blog: http://api.tumblr.com/v2/blog/yourblog.tumblr.com/"
  [blog-name]
  (str base-url blog-name))

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
  "Simple API call"
  [blog-name call api-key & optional-params]
  (to-clj (get-body (str (api-url blog-name) "/" call "?" "api_key=" api-key (map-to-get-params optional-params)))))

(defn info
  "Blog info.  Requires API key."
  [blog-name api-key]
  (to-clj (get-body (str (api-url blog-name) "/info?api_key=" api-key))))


(defn avatar
  "Get an avatar. Takes blog name and (optionally) a size."
  [blog-name & size]
  (get-body (str (api-url blog-name) "/avatar/" size)))

;;(defn likes
;;  "Get a blog's likes.  Optionally you can have set limit and offset.  Ex. (likes blog-name api-key {:limit 5 :offset 2}) "
;;  [blog-name api-key & param-map]
;;  (to-clj (get-body (str (api-url blog-name) "/likes?api_key=" api-key (if param-map (map-to-get-params param-map))))))


(defn likes
  "Get a blog's likes.  Optionally you can have set limit and offset.  Ex. (likes blog-name api-key {:limit 5 :offset 2}) "
  [blog-name api-key & param-map]
  (api-call blog-name "likes" api-key param-map))



(likes tumblr oauth-key)

(+ 25 5)

 ;;(info tumblr oauth-key)
 ;;(avatar blog-name 20)
 ;;(avatar blog-name)


