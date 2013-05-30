package rating

import (
	"appengine"
	"appengine/datastore"

	"encoding/json"
	"net/http"
	"strconv"
	"time"
)

const (
	KIND_RATING = "rating"
)

type Rate struct {
	Name string
	Rate int
	Date time.Time
}

func init() {
	http.HandleFunc("/api/table", getRate)
	http.HandleFunc("/api/postRate", postRate)
}

func getRate(w http.ResponseWriter, r *http.Request) {
	c := appengine.NewContext(r)
	table := make([]*Rate, 0, 300)
	for it := datastore.NewQuery(KIND_RATING).Order("-Rate").Run(c); ; {
		row := new(Rate)
		_, err := it.Next(row)
		if err == datastore.Done {
			break
		}
		if err != nil {
			c.Errorf("ERROR, cannot read table: %s", err)
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		table = table[0 : len(table)+1]
		table[len(table)-1] = row
	}

	if out, err := json.Marshal(table); err == nil {
		w.Header()["Content-Type"] = []string{"application/json;encoding=utf-8"}
		w.Write(out)
	} else {
		c.Errorf("ERROR %s", err)
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}

func postRate(w http.ResponseWriter, r *http.Request) {
	c := appengine.NewContext(r)
	if r.Header.Get("User-Agent") != "rating uploader v1.0" {
		c.Warningf("Illegal access")
		return
	}

	r.ParseForm()

	value, err := strconv.Atoi(r.FormValue("rate"))
	if err != nil {
		c.Errorf("ERROR %s", err)
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	rate := &Rate{
		Name: r.FormValue("name"),
		Rate: value,
	}

	if len(rate.Name) == 0 {
		return
	}

	if unixTime, err := strconv.ParseInt(r.FormValue("time"), 10, 64); err != nil {
		return
	} else {
		rate.Date = time.Unix(unixTime, 0)
	}

	key, err := datastore.NewQuery(KIND_RATING).Filter("Name =", rate.Name).KeysOnly().Run(c).Next(nil)
	if err == datastore.Done {
		key = datastore.NewIncompleteKey(c, KIND_RATING, nil)
	} else if err != nil {
		c.Errorf("ERROR %s", err)
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	datastore.Put(c, key, rate)
}
