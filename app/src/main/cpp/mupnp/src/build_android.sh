NDK=${ANDROID_NDK}
PLATFORM=$NDK/platforms/android-19/arch-arm
PREBUILT=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt
PREFIX=/home/yuhao/ytxPlayer/android/dlna/third_party_libs

echo $PREFIX
     
CFLAGS="-fpic -DANDROID -ffunction-sections -msoft-float -lm -I${PREFIX}/include -I${PREFIX}/include/libxml2"

CROSS_COMPILE=$PREBUILT/linux-x86_64/bin/arm-linux-androideabi-
LDFLAGS="-L${PREFIX}/lib -liconv -lxml2 -lssl -lcrypto"
#LIBS="-liconv -lxml2 -lssl -lcrypto"

export CPPFLAGS="$CFLAGS"
export CXXFLAGS="$CFLAGS"
export CXX="${CROSS_COMPILE}g++ --sysroot=${PLATFORM}"
export LDFLAGS="$LDFLAGS"
export CC="${CROSS_COMPILE}gcc --sysroot=${PLATFORM}"
export NM="${CROSS_COMPILE}nm"
export STRIP="${CROSS_COMPILE}strip"
export RANLIB="${CROSS_COMPILE}ranlib"
export AR="${CROSS_COMPILE}ar"

./configure --prefix=$PREFIX \
--enable-openssl \
--enable-libxml2 \
--disable-examples \
--host=arm-linux

make
make install
