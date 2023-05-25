#!/bin/bash

# Set the duration to run the task in seconds (1 minute = 60 seconds)
duration=60

# Run the task repeatedly until the specified duration is reached
start_time=$(date +%s)
end_time=$((start_time + duration))

while [ $(date +%s) -lt $end_time ]; do
  # Run your task here

  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1
  curl http://localhost:8080/order/1 >> /var/log/myapp/myGetJob.log 2>&1

  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk
  curl --insecure https://www.yourwebappdomain.tk


  # Optional: Add a sleep interval between each iteration
  sleep 1  # Adjust the sleep interval as needed
done

truncate -s 0 /var/lib/docker/containers/**/*.log
truncate -s 0 /var/lib/docker/containers/**/*.log

# Optional: Add any cleanup or final steps after the loop
echo "Task completed."