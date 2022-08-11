#!/bin/bash
container_name=container_name
logs_evaluated_lines=5
logs=/var/log/process_name.log
max_cpu=90

while :
do
    # Lines in file
    num=$(wc -l < $logs)

    counter=0

    # For 'logs_evaluated_lines' lines in logs increase counter if cpu is greater than 100%
    for ((index=$num;index>=$num-$logs_evaluated_lines+1;index--))
    do
    value=$(sed "${index}q;d" $logs)
    percent=$(echo $value | cut -c 20-)
    #echo $percent
    if (( $percent >= max_cpu )); then
    #    echo 'mayor'
        counter=$((counter+1))
    #  else
    #    echo 'menor'
    fi
    done

    echo "$(date +'%d-%m-%Y %H:%M') | Logs up to 100%: ${counter}"
    echo "$(date +'%d-%m-%Y %H:%M') | Logs lines analyzed: ${logs_evaluated_lines}"
    if (( $counter == $logs_evaluated_lines )); then
        echo "$(date +'%d-%m-%Y %H:%M') | CPU Full usage";
        echo "$(date +'%d-%m-%Y %H:%M') | Restarting Container"
        docker restart $container_name
        echo "$(date +'%d-%m-%Y %H:%M') | Container Restarted"
        echo "$(date +'%d-%m-%Y %H:%M') | Container Restarted" >> $logs
    else
        echo "$(date +'%d-%m-%Y %H:%M') | CPU Usage OK"
    fi
    echo "$(date +'%d-%m-%Y %H:%M') |"
    sleep 5
done