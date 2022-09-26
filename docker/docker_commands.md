0) Check Docker overley2 list of directories and size :<br>
``` du -h --max-depth=1 /var/lib/docker/overlay2 | sort -rh | head -25 ```<br>
1) check the disk usage by Docker:<br>
``` docker system df ```<br>
2) Wipe the docker builder cache (if we use Buildkit we very probably need that) :<br>
``` docker builder prune -af ```<br>
3) If we don't want to use the cache of the parent images, we may try to delete them such as :<br>
``` docker image rm -f fooParentImage ```<br>
4) Find docker logs and delete: 
``` find /var/lib/docker/ -type f -name "*.log" -delete ```<br>
