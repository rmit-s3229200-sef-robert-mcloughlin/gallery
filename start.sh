#!/bin/bash

mongoimport -d test -c user --drop ./users.json
mongoimport -d test -c artwork --drop ./artworks.json