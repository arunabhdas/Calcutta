package com.arunabhdas.calcutta.calcuttaui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.ui.screens.InfoItem
import com.arunabhdas.calcutta.ui.theme.createGradientEffect

@Composable
fun UserInfoCard(
    user: GithubUser,
    onFollowersClick: (String) -> Unit,
    onFolloweesClick: (String) -> Unit
) {
    // Define gradient colors for dark mode
    val gradientColors = listOf(
        Color(0xFF1F1F1F),  // Dark gray
        Color(0xFF121212),  // Darker gray
        Color(0xFF0A0A0A)   // Almost black
    )
    
    val gradient = createGradientEffect(colors = gradientColors, isVertical = true)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = gradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "${user.login}'s avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF6200EE), CircleShape) // Purple border
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Username
                Text(
                    text = user.login,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                // Name
                user.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.LightGray,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Bio
                user.bio?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Statistics with highlighting
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        label = "Repositories",
                        value = user.publicRepos.toString(),
                        textColor = Color.White
                    )
                    StatItem(
                        label = "Followers",
                        value = user.followers.toString(),
                        onClick = { onFollowersClick(user.login) },
                        textColor = Color(0xFF6200EE) // Highlight followers in purple
                    )
                    StatItem(
                        label = "Following",
                        value = user.following.toString(),
                        onClick = { onFolloweesClick(user.login) },
                        textColor = Color.White
                    )
                }
                
                // Additional info
                user.location?.let {
                    InfoItem(label = "Location", value = it, textColor = Color.LightGray)
                }
                
                user.company?.let {
                    InfoItem(label = "Company", value = it, textColor = Color.LightGray)
                }
            }
        }
    }
}
