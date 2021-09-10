#### commands

    podman run --name kubernetes-native-app -p 8080:8080 lib/kubernetes-cloud-native-app-0.0.1-SNAPSHOT.jar
    podman rm registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app
    podman build -t registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app .
    podman run -p 8080:8080 registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app:latest
    `podman push registry.gitlab.com/jatinderkumarchaurasia/flowkind/kubernetes-native-app:latest`
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

    to set config to use cluster
    kubectl config set-cluster gke_flowkind-325606_us-central1-c_flowkind
    to set config to use context
    kubectl config --kubeconfig=config use-context gke_flowkind-325606_us-central1-c_flowkind
    kubectl get namespace
    kubectl get secrets
    kubectl config set-context --current --namespace=<namespace> -- to switch between namespace
    kubectl delete secret gitlab-auth -- to delete secret that created 
    kubectl get services --namespace flowkind
    kubectl get pod --namespace flowkind
    kubectl get deployments --namespace flowkind
    watch -n 1 kubectl get all

#### to install nginx-controller , i followed some tutorial from gcloud -[Doc Link](https://cloud.google.com/community/tutorials/nginx-ingress-gke)

    helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
    helm repo update
    helm install nginx-ingress ingress-nginx/ingress-nginx
    kubectl get deployment nginx-ingress-nginx-ingress
    kubectl get service nginx-ingress-nginx-ingress
    kubectl get service nginx-ingress-nginx-ingress

[comment]: <> (apiVersion: networking.k8s.io/v1)

[comment]: <> (kind: Ingress)

[comment]: <> (metadata:)

[comment]: <> (annotations:)

[comment]: <> (kubernetes.io/ingress.class:)

[comment]: <> (name: example)

[comment]: <> (namespace: foo)

[comment]: <> (spec:)

[comment]: <> (rules:)

[comment]: <> (- host: www.example.com)

[comment]: <> (http:)

[comment]: <> (paths:)

[comment]: <> (- backend:)

[comment]: <> (serviceName: exampleService)

[comment]: <> (servicePort: 80)

[comment]: <> (path: /)

[comment]: <> (# This section is only required if TLS is to be enabled for the Ingress)

[comment]: <> (tls:)

[comment]: <> (- hosts:)

[comment]: <> (- www.example.com)

[comment]: <> (secretName: example-tls)

[comment]: <> (If TLS is enabled for the Ingress, a Secret containing the certificate and key must also be provided:)

[comment]: <> (apiVersion: v1)

[comment]: <> (kind: Secret)

[comment]: <> (metadata:)

[comment]: <> (name: example-tls)

[comment]: <> (namespace: foo)

[comment]: <> (data:)

[comment]: <> (tls.crt: <base64 encoded cert>)

[comment]: <> (tls.key: <base64 encoded key>)

[comment]: <> (type: kubernetes.io/tls)

#### how to check watch -n 1 kubectl get all

    watch -n 1 kubectl get all
    NAME                                                          READY   STATUS    RESTARTS   AGE
    pod/kubernetes-native-app-6d49d8fb5f-28dzg                    1/1     Running   0          48m
    pod/nginx-ingress-ingress-nginx-controller-5bbd94d775-wjtfc   1/1     Running   0          12m
    
    NAME                                                       TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)                      AGE
    service/kubernetes-native-app                              ClusterIP	  10.108.13.102   <none>          80/TCP                       47m
    service/nginx-ingress-ingress-nginx-controller             LoadBalancer   10.108.1.221    104.198.39.59   80:30597/TCP,443:30950/TCP   13m
    service/nginx-ingress-ingress-nginx-controller-admission   ClusterIP      10.108.15.225   <none>          443/TCP                      13m
    
    NAME                                                     READY   UP-TO-DATE   AVAILABLE   AGE
    deployment.apps/kubernetes-native-app                    1/1     1            1           48m
    deployment.apps/nginx-ingress-ingress-nginx-controller   1/1     1            1           13m
    
    NAME                                                                DESIRED   CURRENT   READY   AGE
    replicaset.apps/kubernetes-native-app-6d49d8fb5f                    1         1         1	48m
    replicaset.apps/nginx-ingress-ingress-nginx-controller-5bbd94d775   1         1         1	13m

##### preconfiguration to use google kubernetes

    1. gcloud init - it will give you option to set your project 
    2. gcloud auth login will do the same 
    3. gcloud container clusters get-credentials my-first-cluster-1 --zone us-central1-c --project flowkind-app - to get credentials
       it will add the cluster configuration to your ~/.kube/config file
    4. kubectl get nodes - will give all the nodes that are present on cluster
    5. if you have multiple clusters that are running on - you need to check
       i) kubectl config view - give you all the cluster and their context details
       ii) kubectl config set-cluster cluster_name
       iii) the namespace default will auto created in cluster
       iii) to create new namespace and switch to use that namespace
              kubectl create namespace namespace_name
              kubectl config set-context --current --namespace namespace_name


      