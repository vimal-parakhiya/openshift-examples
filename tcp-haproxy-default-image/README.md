# Openshift TCP HA Proxy Exaples

## Openshift TCP HA Proxy - Default Image


### Deploy TCP HA Proxy
- Start Minishift 

    `minishift start`
- Login into OC 
    
    `oc login -u system:admin`
- Create new project (Blue) 

    `oc new-project oc-tcp-haproxy --description="Openshift TCP HA Proxy" --display-name="Openshift TCP HA Proxy"`

- Setup Minishift docker-env

    `minishift docker-env`
    
    `eval $(minishift docker-env)`

- Login into Docker Registry

    `docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)`
    
- Create Docker Image

    `docker build -t os-tcp-haproxy-default-img tcp-haproxy-default-image/`
    
- Tag Docker Image

    `docker tag os-tcp-haproxy-default-img $(minishift openshift registry)/oc-tcp-haproxy/os-tcp-haproxy-default-img`
    
- Push Docker Image    
    `docker push $(minishift openshift registry)/oc-tcp-haproxy/os-tcp-haproxy-default-img`
    
- Create Config map 
    `oc create configmap tcp-haproxy-default-img --from-file=tcp-haproxy-default-image/haproxy-config/haproxy-config.template`
    
- Apply Deployment & Service Configurations 
    
    `oc apply -f tcp-haproxy-default-image/os-tcp-haproxy-default-img.json`

- Define Route to expose the `tcp-haproxy-default-image`

    `oc expose service/tcp-haproxy-default-img --hostname=tcp-haproxy-default-img.192.168.64.2.nip.io`

- Access & Verify the Service
    `curl http://tcp-haproxy-default-img.192.168.64.2.nip.io`
    
   
   
