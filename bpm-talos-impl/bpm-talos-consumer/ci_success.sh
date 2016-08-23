#!/usr/bin/env bash
mkdir outfile
cp bpm-talos-impl/bpm-talos-consumer/appspec.yml outfile/
cp bpm-talos-impl/bpm-talos-consumer/start.sh.etpl outfile/
cp bpm-talos-impl/bpm-talos-consumer/validate.sh outfile/
unzip -d outfile/ bpm-talos-impl/bpm-talos-consumer/target/bpm-talos-consumer.zip