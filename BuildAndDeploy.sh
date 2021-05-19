mvn clean install
cd infrastructure
cdk synth --profile $1
cdk deploy --profile $1
