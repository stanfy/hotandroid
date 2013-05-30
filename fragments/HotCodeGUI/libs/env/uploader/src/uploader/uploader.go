package main

import (
	"encoding/base64"
	"flag"
	"fmt"
	"net/http"
	"os"
	"strconv"
)

var (
	nameArg = flag.String("name", "", "")
	rateArg = flag.Int("rate", 0, "")
	timeArg = flag.String("time", "", "")
)

const (
	endpoint = "aHR0cDovL3N0YW5meS1lbmdpbmUuYXBwc3BvdC5jb20vYXBpL3Bvc3RSYXRl"
	agent    = "cmF0aW5nIHVwbG9hZGVyIHYxLjA="
)

func main() {
	flag.Parse()

	if os.ExpandEnv("${TRAVIS_JOB_ID}") == "" || os.ExpandEnv("${TRAVIS_BRANCH}") == "" || os.ExpandEnv("${TRAVIS_REPO_SLUG}") == "" {
		return
	}

	ok := "NO"

	target, _ := base64.StdEncoding.DecodeString(endpoint)
	userAgent, _ := base64.StdEncoding.DecodeString(agent)
	req, _ := http.NewRequest("GET", string(target)+"?name="+*nameArg+"&rate="+strconv.Itoa(*rateArg)+"&time="+*timeArg, nil)
	if req != nil {
		req.Header.Add("User-Agent", string(userAgent))
		resp, _ := new(http.Client).Do(req)

		if resp != nil && resp.StatusCode == 200 {
			ok = "YES"
		}
		fmt.Println("Uploaded: " + ok)
	}
}
