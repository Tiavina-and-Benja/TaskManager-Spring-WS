Parfait 🔥 — voici **TOUS les endpoints nécessaires** pour ton projet **Task Manager API**, organisés proprement pour respecter ton cahier des charges 👇

---

# 🚀 📦 BASE URL

```http
/api
```

---

# 🔐 1. AUTH (JWT)

## 🔑 Authentification

```http
POST /api/auth/register
POST /api/auth/login
```

### Exemple :

```json
{
  "email": "test@mail.com",
  "password": "123456"
}
```

👉 Retour :

```json
{
  "token": "jwt_token_here"
}
```

---

# 👤 2. USER

```http
GET    /api/users
GET    /api/users/{id}
DELETE /api/users/{id}
```

👉 ADMIN seulement

---

# 📁 3. PROJECT

```http
POST   /api/projects
GET    /api/projects
GET    /api/projects/{id}
PUT    /api/projects/{id}
DELETE /api/projects/{id}
```

---

## 🔎 Projets d’un utilisateur

```http
GET /api/projects/user/{userId}
```

---

# ✅ 4. TASK

```http
POST   /api/tasks
GET    /api/tasks
GET    /api/tasks/{id}
PUT    /api/tasks/{id}
DELETE /api/tasks/{id}
```

---

## 🔎 Filtres (IMPORTANT 🔥)

```http
GET /api/tasks?status=TODO&priority=HIGH&projectId=1
```

👉 filtres combinés :

* status
* priority
* project
* deadline

---

## 📁 Tâches d’un projet

```http
GET /api/tasks/project/{projectId}
```

---

# 🧠 5. PRIORISATION (API COMPLEXE)

```http
GET /api/tasks/prioritized?userId=1
```

👉 Retour :

```json
[
  {
    "taskId": 1,
    "title": "Projet Spring",
    "score": 113
  }
]
```

---

# 📊 6. DASHBOARD (API COMPLEXE)

```http
GET /api/dashboard
```

👉 Retour :

```json
{
  "totalTasks": 100,
  "completedTasks": 70,
  "completionRate": 70,
  "averageDuration": 3.5
}
```

---

## 📊 Dashboard par période (bonus 🔥)

```http
GET /api/dashboard?start=2026-01-01&end=2026-03-30
```

---

# 🏷️ 7. TAG

```http
POST   /api/tags
GET    /api/tags
GET    /api/tags/{id}
DELETE /api/tags/{id}
```

---

# 🔗 8. TASK-TAG (relation N-N)

## Ajouter un tag à une tâche

```http
POST /api/task-tags
```

```json
{
  "taskId": 1,
  "tagId": 2
}
```

---

## Supprimer un tag d’une tâche

```http
DELETE /api/task-tags/{id}
```

---

## 🔎 Récupérer les tâches par tag

```http
GET /api/tasks/tag/{tagId}
```

---

# 📈 9. TASK HISTORY

```http
POST   /api/task-history
GET    /api/task-history/task/{taskId}
```

---

# 🔗 10. HATEOAS (exemple)

```http
GET /api/tasks/{id}
```

👉 Retour :

```json
{
  "id": 1,
  "title": "Task A",
  "_links": {
    "self": { "href": "/api/tasks/1" },
    "project": { "href": "/api/projects/2" }
  }
}
```

---

# 🔐 11. Sécurité (roles)

| Endpoint     | Role   |
| ------------ | ------ |
| /auth/**     | public |
| /users/**    | ADMIN  |
| /projects/** | USER   |
| /tasks/**    | USER   |
| /dashboard   | ADMIN  |

---

# 📮 12. Structure Postman

Collection :

* Auth
* Users
* Projects
* Tasks
* Tags
* TaskTags
* Dashboard

---

# 🎯 Résumé

✔ CRUD complet
✔ filtres dynamiques
✔ relation N-N
✔ JWT + roles
✔ 2 APIs complexes :

* priorisation 🧠
* dashboard 📊
  ✔ HATEOAS

---

# 🚀 Si tu veux aller encore plus loin

Je peux te générer :

* 🔥 Controllers Spring complets (copier-coller)
* 🔐 config JWT prête
* 📊 requêtes SQL dashboard
* 📁 projet structuré complet
* 📄 Swagger déjà configuré

👉 Dis-moi juste : **“génère les controllers”** ou **“fais-moi le backend complet”** 👍
