#!/bin/bash

mongoimport -d test -c user --drop ./users.json
mongoimport -d test -c art --drop ./artworks.json