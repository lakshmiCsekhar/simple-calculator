mvn clean install
cd infrastructure
echo "Deploy will use below AWS account details"
echo "Profile = $1"
echo "Account = $2"
echo "Region = $3"
cdk synth --profile $1 -c accountId=$2 -c region=$3
cdk deploy --profile $1 -c accountId=$2 -c region=$3
