# Backend DevOps Pronto 🚀

> Spring Boot application with complete DevOps setup including Docker, Kubernetes, CI/CD pipeline and monitoring.

## 🛠️ Tech Stack

- **Backend:** Spring Boot + MongoDB + JWT
- **Container:** Docker
- **Orchestration:** Kubernetes
- **CI/CD:** Jenkins + ArgoCD
- **Monitoring:** Prometheus + Grafana

## 📋 Prerequisites

- Docker Desktop
- Kubernetes
- kubectl CLI
- Helm v3
- Jenkins
- Git


## 🚀 Quick Start

1. **Create Namespaces**
```bash
kubectl create namespace pronto
kubectl create namespace prontoargocd
kubectl create namespace monitoring
```


## 🔗 Access Points

- **Backend API:** http://localhost:8080
- **Prometheus:** http://localhost:30091
- **Grafana:** http://localhost:30092
- **ArgoCD:** https://localhost:8082/applications
- **Metrics:** http://localhost:8086/actuator/prometheus

## 📊 Monitoring Features

- JVM & Application Metrics
- MongoDB Performance
- Kubernetes Cluster Health
- Custom HTTP Metrics

## 🔄 CI/CD Pipeline

1. Code Checkout
2. Build Spring Boot App
3. Build Docker Image
4. Push to Registry
5. K8s Deployment

## 🔒 Security

- JWT Authentication
- K8s Secrets Management
- RBAC Configuration

