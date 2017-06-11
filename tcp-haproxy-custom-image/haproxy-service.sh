#!/usr/bin/env bash
echo "Starting HA proxy with configuration file: $CONFIG_FILE"

mkdir /tmp/watch-configmap
cp $CONFIG_FILE /tmp/watch-configmap/tmp.config
/usr/sbin/haproxy -f $CONFIG_FILE -p haproxy.pid

echo "Started HA Proxy..."

while true;
do
echo `date`
if cmp $CONFIG_FILE /tmp/watch-configmap/tmp.config; then
    echo "No change in HA proxy configuration..."
    sleep 5;
else
    echo "HA proxy configuration changed... reloading.."
    cp $CONFIG_FILE /tmp/watch-configmap/tmp.config
    /usr/sbin/haproxy -f $CONFIG_FILE -p haproxy.pid -sf $(cat haproxy.pid)
    echo "HA proxy configuration reloaded.."
fi
echo "------------------------------------------------------"
done;
