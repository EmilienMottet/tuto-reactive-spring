#!/usr/bin/env bash

for i in `seq 1 80`; do
    curl -s -w ' : %{time_starttransfer}\n' localhost:8080/persons &
done
