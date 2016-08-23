#!/usr/bin/env bash
mkdir outfile
cp bpm-talos-impl/bpm-talos-search-impl/appspec.yml outfile/
cp bpm-talos-impl/bpm-talos-search-impl/start.sh.etpl outfile/
cp bpm-talos-impl/bpm-talos-search-impl/validate.sh.etpl outfile/
unzip -d outfile/ bpm-talos-impl/bpm-talos-search-impl/target/bpm-talos-search.zip