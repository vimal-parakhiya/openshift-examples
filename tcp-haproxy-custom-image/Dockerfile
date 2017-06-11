FROM openshift/origin-haproxy-router:v1.5.0
COPY haproxy-config custom-config
COPY haproxy-service.sh .
RUN cp haproxy-service.sh /tmp # Hack to overcome chmod file permission issue
RUN chmod +x /tmp/haproxy-service.sh
EXPOSE 15050 16060 17070 18080 19090
ENTRYPOINT sh -c "/tmp/haproxy-service.sh"
#ENTRYPOINT sh -c "/usr/sbin/haproxy-systemd-wrapper -f $CONFIG_FILE -p haproxy.pid"
#ENTRYPOINT /usr/sbin/haproxy -f /opt/custom-haproxy-config/haproxy.config -p haproxy.pid -sf $(cat haproxy.pid)