export async function getFetch(url: string, city: string, date: string, token: string) {
  const queryParams = new URLSearchParams({ city, date });

  const response = await fetch(`${url}?${queryParams.toString()}`, {
    method: 'GET',
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json",
    }
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Request failed");
  }

  const data: string = await response.json();
  return data;
}

export async function postFetch(url: string, data: string) {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data)
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Request failed");
  }

  return response;
}
