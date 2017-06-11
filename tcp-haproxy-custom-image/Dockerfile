FROM openshift/origin-haproxy-router:v1.5.0
COPY haproxy-config custom-config

EXPOSE 15050 16060 17070 18080 19090
#ENTRYPOINT ["/bin/bash"]
ENTRYPOINT sh -c "/usr/sbin/haproxy-systemd-wrapper -f $CONFIG_FILE -p haproxy.pid"
#ENTRYPOINT /usr/sbin/haproxy -f /opt/custom-haproxy-config/haproxy.config -p haproxy.pid -sf $(cat haproxy.pid)