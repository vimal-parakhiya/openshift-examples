 # OpenShift Blue/Green Service Deployment - Single Service, but different Selector
 
 - Create new project
 
    `oc new-project oc-greeting-blue-green --description="Openshift Greeting Project - Blue/Green" --display-name="Openshift Greeting - Blue/Green"`
 
 - Build and Tag application - Blue version
    
    Set `service.type=Blue` in `application.properties` in `greeting-service` project and then run following command.
 
    `mvn clean install -f greeting-service/ && cp greeting-service/target/greeting-service-1.0.0.war docker/os-greeting-service/ && docker build -t os-greeting-service-blue docker/os-greeting-service/`
 
- Build and Tag application - Green version
    
    Set `service.type=Green` in `application.properties` in `greeting-service` project and then run following command.

    `mvn clean install -f greeting-service/ && cp greeting-service/target/greeting-service-1.0.0.war docker/os-greeting-service/ && docker build -t os-greeting-service-green docker/os-greeting-service/`
 
- Tag & Push Images in OpenShift Project ImageStreams
    ```
     docker tag os-greeting-service-green $(minishift openshift registry)/oc-greeting-blue-green/os-greeting-service-green
     docker tag os-greeting-service-blue $(minishift openshift registry)/oc-greeting-blue-green/os-greeting-service-blue
     docker push $(minishift openshift registry)/oc-greeting-blue-green/os-greeting-service-green
     docker push $(minishift openshift registry)/oc-greeting-blue-green/os-greeting-service-blue
     ```
 - Deploy Configuration - Blue
    ```
    oc apply -f blue-green-different-selector/deployment-config-blue.json
    ```
 - Deploy Configuration - Green
    ```
    oc apply -f blue-green-different-selector/deployment-config-green.json
    ```
 - Deploy Service using Blue Selector
    ```
    oc apply -f blue-green-different-selector/service-blue-selector.json
    ```
 - Expose service using Route
 
    `oc expose service/greeting-service --name=greeting-service-route --hostname=greeting.192.168.64.2.nip.io`
 
## Run following command to switch from Blue to Green and vice-versa at every 10 seconds
  ```
  while true; 
  do oc patch svc/greeting-service -n oc-greeting-blue-green --patch '{"spec":{"selector": {"name":"greeting-service-blue", "deploymentconfig": "greeting-service-blue"}}}' 
  sleep 10 
  oc patch svc/greeting-service -n oc-greeting-blue-green --patch '{"spec":{"selector": {"name":"greeting-service-green", "deploymentconfig": "greeting-service-green"}}}'  
  sleep 10 
  echo "--------------------------------------------" 
  done;
  ```

Note: Test run indicates that there will be zero downtime as shown below. During this run, route is toggled 8 times by switching target service from Green to Blue and Blue to Green.

`Down Time Statistics: DownTimeTracker{downTimes=[] Average Down Time (ms): 0}`