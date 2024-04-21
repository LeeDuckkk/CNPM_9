<template>
  <div>
    <!-- Hero Section -->
    <section id="hero">
      <div class="image-container">
        <div class="hero-text">
          CLB Taekwondo-PTIT<br>

          <i style="font-size: 14px;">Chào mừng đến với CLB Taekwondo-PTIT</i>
        </div>
      </div>
      <div class="scroll-animation">Scroll Down</div>
    </section>

    <!-- Welcome Message -->
    <section id="welcome">
      <h1>Chào mừng đến với CLB Taekwondo-PTIT</h1>
    </section>

    <!-- About Us and Contact Section -->
    <section id="about-contact">
      <h2>About Us</h2>
      <p>Your about us content goes here.</p>

      <h2>Contact</h2>
      <p>Your contact information goes here.</p>
    </section>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
}
</script>

<style scoped>
.image-container {
  height: 70vh;
  background: url("@/assets/taekwondo.jpg") no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.image-container::before {
  content: "";
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
}

.hero-text {
  color: white;
  font-size: 2em;
  text-align: center;
  position: relative;
  z-index: 1;
}

.image-container {
  width: 100%;
  object-fit: cover;
  background-color: rgba(0, 0, 0, 0.5);
}

#hero {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

#hero img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.scroll-animation {
  position: absolute;
  bottom: 20px;
  animation: scroll 2s infinite;
  color: white;
}

@keyframes scroll {
  0% { opacity: 1; }
  50% { opacity: 0; }
  100% { opacity: 1; }
}

#welcome, #about-contact {
  padding: 50px;
  text-align: center;
}
</style>