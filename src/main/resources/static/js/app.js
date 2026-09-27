/* QUEST Global Frontend Script */

async function apiRequest(url, method = 'GET', data = null) {
  const options = {
    method: method,
    headers: {
      'Content-Type': 'application/json'
    }
  };
  if (data && method !== 'GET') {
    options.body = JSON.stringify(data);
  }
  try {
    const response = await fetch(url, options);
    return await response.json();
  } catch (err) {
    console.error('API Error:', err);
    return { success: false, message: 'Network or server error occurred.' };
  }
}

// Show alert message banner
function showAlert(message, type = 'success') {
  const alertBox = document.getElementById('alert-box');
  if (alertBox) {
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;
    alertBox.style.display = 'block';
    setTimeout(() => {
      alertBox.style.display = 'none';
    }, 4000);
  } else {
    alert(message);
  }
}

// Opportunity Save Toggle
async function toggleSaveOpportunity(id, btnElement) {
  const res = await apiRequest(`/api/opportunities/${id}/save`, 'POST');
  if (res.success) {
    if (res.saved) {
      btnElement.innerText = '★ Saved';
      btnElement.classList.remove('btn-secondary');
      btnElement.classList.add('btn-primary');
    } else {
      btnElement.innerText = '☆ Save';
      btnElement.classList.remove('btn-primary');
      btnElement.classList.add('btn-secondary');
    }
  }
}

// Opportunity Apply
async function applyOpportunity(id, btnElement) {
  const res = await apiRequest(`/api/opportunities/${id}/apply`, 'POST');
  if (res.success) {
    alert('Application submitted successfully!');
    if (btnElement) {
      btnElement.innerText = '✓ Applied';
      btnElement.disabled = true;
      btnElement.classList.add('btn-secondary');
    }
  } else {
    alert(res.message);
  }
}

// Mark Notification Read
async function markNotificationRead(id, element) {
  const res = await apiRequest(`/api/notifications/${id}/read`, 'PUT');
  if (res.success && element) {
    element.classList.remove('unread');
    element.style.opacity = '0.6';
  }
}
