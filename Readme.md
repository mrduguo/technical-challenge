# Paint batch optimizer service

## Purpose

This service provides solutions for the following problem.

Our users own paint factories. There are N different colors they can mix, and each color can be prepared "matte" or "glossy". So, you can make 2N different types of paint.

Each of their customers has a set of paint types they like, and customers will be satisfied if you have at least one of those types prepared. At most one of the types a customer likes will be a "matte".

Our user wants to make N batches of paint, so that:

There is exactly one batch for each color of paint, and it is either matte or glossy. For each customer, user makes at least one paint that they like. The minimum possible number of batches are matte (since matte is more expensive to make). This service finds whether it is possible to satisfy all customers given these constraints, and if it is, what paint types you should make. If it is possible to satisfy all your customers, there will be only one answer which minimizes the number of matte batches.

Input

Integer N, the number of paint colors,  integer M, the number of customers. A list of M lists, one for each customer, each containing: An integer T >= 1, the number of paint types the customer likes, followed by T pairs of integers "X Y", one for each type the customer likes, where X is the paint color between 1 and N inclusive, and Y is either 0 to indicate glossy, or 1 to indicated matte. Note that: No pair will occur more than once for a single customer. Each customer will have at least one color that they like (T >= 1). Each customer will like at most one matte color. (At most one pair for each customer has Y = 1). 

Output

The string "IMPOSSIBLE", if the customers' preferences cannot be satisfied; OR N space-separated integers, one for each color from 1 to N, which are 0 if the corresponding paint should be prepared glossy, and 1 if it should be matte.




### Examples

http://0.0.0.0:8080/v1/?input={%22colors%22:1,%22customers%22:2,%22demands%22:[[1,1,1],[1,1,0]]}
IMPOSSIBLE

http://0.0.0.0:8080/v1/?input={%22colors%22:5,%22customers%22:3,%22demands%22:[[1,1,1],[2,1,0,2,0],[1,5,0]]}
1 0 0 0 0

## Limitations

None of our users produce more than 2000 different colors, or have more than 2000 customers. (1 <= N <= 2000 1 <= M <= 2000)
The sum of all the T values for the customers in a request will not exceed 3000.


## Developer Guide



### Requirements

* JAVA 7 or newer

#### build artifact locally

```
./gradlew
```

#### run the application directly without build

```
./gradlew run
```

Then the app can be accessed from:

* [http://localhost:8080](http://localhost:8080)




#### build and deploy all in one to aws


> This require you have `~/.aws/credentials` configured and ElasticBeanstalk Environment created.

```

mavenReleaseRepoUrl=https://s3-eu-west-1.amazonaws.com/elasticbeanstalk-eu-west-1-261986573496/maven-repo/ \
./gradlew clean build aws_eb_deploy
 
```


After the deploy finished, you can verify the application from the built-in info endpoint:
 [http://lab-tc.eu-west-1.elasticbeanstalk.com/v1/serviceability/info](http://lab-tc.eu-west-1.elasticbeanstalk.com/v1/serviceability/info)

* [sample output](/src/doc/sample-build-logs/aws-build-and-deploy-all-in-one.log)



#### API Version 1


```

curl 'http://lab-tc.eu-west-1.elasticbeanstalk.com/v1/?input=\{%22colors%22:1,%22customers%22:2,%22demands%22:\[\[1,1,1\],\[1,1,0\]\]\}'

```


#### API Version 2


```

curl  http://lab-tc.eu-west-1.elasticbeanstalk.com/v2/ -H 'Content-Type: application/json' -d '{"colors":1,"customers":2,"demands":[[1,1,1],[1,1,0]]}'

```

#### Serviceability Endpoints

* http://lab-tc.eu-west-1.elasticbeanstalk.com/v1/serviceability/info
* http://lab-tc.eu-west-1.elasticbeanstalk.com/v1/serviceability/health
* http://lab-tc.eu-west-1.elasticbeanstalk.com/v1/serviceability/metrics
