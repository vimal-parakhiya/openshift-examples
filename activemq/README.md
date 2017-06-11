 # OpenShift ActiveMQ Deployment 
 
 **TODO**: Mount Kaha DB on persistent volume.
 
 - Create new project
 
    `oc new-project oc-activemq --description="Openshift ActiveMQ" --display-name="Openshift ActiveMQ"`
 
 - Build ActiveMQ Docker Image
    
    `docker build -t os-java-maven-base docker/os-java-maven-base/`
    `docker build -t os-activemq docker/os-activemq/`
 
- Tag & Push Docker Image
    
    ```
     docker tag os-activemq $(minishift openshift registry)/oc-activemq/os-activemq
     docker push $(minishift openshift registry)/oc-activemq/os-activemq
     ```
 - Deploy ActiveMQ
    ```
    oc apply -f activemq/openshift-activemq.json
    oc deploy dc/activemq --latest
    ```
 - Expose ActiveMQ - Admin console 
 
    `oc expose service activemq --port=8161 --name=activemq-web  --hostname=activemq-web.192.168.64.2.nip.io`

 - Build, Tag & Push ActiveMQ Sender/Receiver
     ```
     mvn clean install -f activemq/springboot-activemq && cp activemq/springboot-activemq/target/springboot-activemq-1.0.0.jar activemq/springboot-activemq/docker/runtime && docker build -t os-amq-tester activemq/springboot-activemq/docker/runtime
     docker tag os-amq-tester $(minishift openshift registry)/oc-activemq/os-amq-tester
     docker push $(minishift openshift registry)/oc-activemq/os-amq-tester
     ```

- Build, Tag & Push HA Proxy Image
    ```
    docker build -t os-amq-tcp-haproxy tcp-haproxy-custom-image/
    docker tag os-amq-tcp-haproxy $(minishift openshift registry)/oc-activemq/os-amq-tcp-haproxy
    docker push $(minishift openshift registry)/oc-activemq/os-amq-tcp-haproxy    
    ```

### WIP