#!/usr/bin/env bash

for i in `seq 1 8`; do
    curl -s -w ' : %{time_starttransfer}\n' localhost:8080/persons &
done
