# Openshift TCP HA Proxy Exaples

## Openshift TCP HA Proxy - Custom Image


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

    `docker build -t os-tcp-haproxy-custom-img tcp-haproxy-custom-image/`
    
- Tag Docker Image

    `docker tag os-tcp-haproxy-custom-img $(minishift openshift registry)/oc-tcp-haproxy/os-tcp-haproxy-custom-img`
    
- Push Docker Image    
    `docker push $(minishift openshift registry)/oc-tcp-haproxy/os-tcp-haproxy-custom-img`
    
- Create Config map 
    `oc create configmap tcp-haproxy-custom-img --from-file=tcp-haproxy-custom-image/haproxy-config/haproxy.config`
    
- Apply Deployment & Service Configurations 
    
    `oc apply -f tcp-haproxy-custom-image/os-tcp-haproxy-custom-img.json`

- Define Route to expose the `tcp-haproxy-custom-image`

    `oc expose service/tcp-haproxy-custom-img --hostname=tcp-haproxy-custom-img.192.168.64.2.nip.io`

- Access & Verify the Service
    `curl http://tcp-haproxy-custom-img.192.168.64.2.nip.io`
    
   
   
