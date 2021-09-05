#### commands

    podman run --name kubernetes-native-app -p 8080:8080 lib/kubernetes-cloud-native-app-0.0.1-SNAPSHOT.jar
    podman rm registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app
    podman build -t registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app .
    podman run -p 8080:8080 registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app:latest
    podman push registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app:latest
    skopeo list-tags docker://registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app
    skopeo inspect docker://registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app:latest

#### basic podman commands

    podman container list
    podman image list
    podman rmi imagename
    podman pod list

#### to create deployment

    kubectl create deployment kubernetes-native-app --image registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app:latest -o yaml --dry-run=client > k8s/deployment.yaml

#### to create service

    kubectl create service clusterip kubernetes-native-app --tcp 80:8080 -o yaml --dry-run=client > k8s/service.yaml

#### to apply those

    kubectl apply -f k8s

#### to create secret

    kubectl create secret docker-registry <name> --docker-server=DOCKER_REGISTRY_SERVER --docker-username=DOCKER_USER --docker-password=DOCKER_PASSWORD --docker-email=DOCKER_EMAIL
    example
    kubectl create secret docker-registry gitlab-auth --docker-server=https://registry.gitlab.com --docker-username=yourusername --docker-password=yourpassword --docker-email=jatinderkumarchaurasia@gmail.com

#### to forward the port to local

    kubectl port-forward service/kubernetes-native-app 8080:80

#### Networking

##### finding cluster ip

    kubectl get pod -o wide

##### finding service ip

    kubectl get service --all-namespaces

#### some other commands

    kubectl get services --namespace flowkind
    kubectl get pod --namespace flowkind
    kubectl get deployments --namespace flowkind
    watch -n 1 kubectl get all
