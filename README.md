# Allegro zadanie

## Instalacja

API Githuba posiada limit zapytań, z tego powodu należy

1. Wygenerować token o tutaj https://github.com/settings/tokens/ przy czym nie trzeba dawać żadnych
   dodatkowych uprawnień
2. Wkleić w pliku *gradle.properties* do zmiennej `github_token`

## Plany na przyszłość

Czyli co można by było dodać, aby było jeszcze lepiej:

- cachowanie danych pobranych zarówno contributorów jak i repozytoriów. W przypadku Paging 3 można
  wykorzystać np. `RemoteMediator`, chociaż trzeba brać pod uwagę, że jest to *experimental api*,
- treść zadania temu trochę przeczy, bo miało być tylko dla Allegro, ale fajnie by było zrobić
  wyszukiwanie repozytoriów np. danego użytkownika albo po nazwie,
- więcej testów!!! Jakby ktoś wiedział jak testować Paging 3 to zapraszam na StackOverflow, jest
  duży popyt, a co za tym idzie łatwa reputacja do zgarnięcia.
