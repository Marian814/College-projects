library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

entity Unitate_control is
    Port ( adauga_cifra : in std_logic;
           reset : in std_logic;
           clk : in std_logic;
           iesire_numarator : in std_logic_vector(3 downto 0);
           liber_ocupat : out std_logic;
           led_introdu : out std_logic;
           data : out std_logic_vector(11 downto 0));
end Unitate_control;

architecture Behavioral of Unitate_control is

type stare_t is (Liber, 
                 Astept_caracterul1, 
                 Astept_caracterul2, 
                 Astept_caracterul3, 
                 Ocupat,
                 Ocupat_Astept_caracterul1, 
                 Ocupat_Astept_caracterul2, 
                 Ocupat_Astept_caracterul3, 
                 Verificare_cod);
signal Stare, Nx_Stare : stare_t;
signal cod_introdus_intermediar : std_logic_vector(11 downto 0);
signal cod_verificare_intermediar : std_logic_vector(11 downto 0);

begin

process(reset, clk)
begin
if Reset = '1' then
    Stare <= Liber;
elsif clk'event and clk = '1' then
    Stare <= Nx_Stare;
end if;
end process;

tranzitii : process(Stare, adauga_cifra)
begin
case Stare is
    when Liber => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Astept_caracterul1;
        else 
            Nx_Stare <= Liber;
        end if;
        
    when Astept_caracterul1 => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Astept_caracterul2;
        else 
            Nx_Stare <= Astept_caracterul1;
        end if;
        
    when Astept_caracterul2 => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Astept_caracterul3;
        else 
            Nx_Stare <= Astept_caracterul2;
        end if;
        
    when Astept_caracterul3 => 
       
        if  adauga_cifra = '1' then
            Nx_Stare <= Ocupat;
        else 
            Nx_Stare <= Astept_caracterul3;
        end if; 
                
    when Ocupat => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Ocupat_Astept_caracterul1;
        else 
            Nx_Stare <= Ocupat;
        end if;
        
    when Ocupat_Astept_caracterul1 => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Ocupat_Astept_caracterul2;
        else 
            Nx_Stare <= Ocupat_Astept_caracterul1;
        end if;
        
    when Ocupat_Astept_caracterul2 => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Ocupat_Astept_caracterul3;
        else 
            Nx_Stare <= Ocupat_Astept_caracterul2;
        end if;
        
    when Ocupat_Astept_caracterul3 => 
        if  adauga_cifra = '1' then
            Nx_Stare <= Verificare_cod;
        else 
            Nx_Stare <= Ocupat_Astept_caracterul3;
        end if;
        
    when Verificare_cod =>
        if cod_introdus_intermediar = cod_verificare_intermediar then
            Nx_Stare <= Liber;
        else
            Nx_Stare <= Ocupat;
        end if;
        
    when others =>
    
end case;
end process;

ce_face : process(Stare, adauga_cifra, clk)
begin
led_introdu <= '0';
if rising_edge (clk) then
case Stare is
    when Liber => 
        cod_introdus_intermediar <= (others => '0');
        cod_verificare_intermediar <= (others => '0');
        data <= (others => '0');
        led_introdu <= '0';
        liber_ocupat <= '0';
        
    when Astept_caracterul1 => 
        cod_introdus_intermediar(3 downto 0) <= iesire_numarator;
        data <= cod_introdus_intermediar;
        led_introdu <= '1';
        liber_ocupat <= '0';
        
    when Astept_caracterul2 =>
        cod_introdus_intermediar(7 downto 4) <= iesire_numarator; 
        data <= cod_introdus_intermediar;
        led_introdu <= '1';
        liber_ocupat <= '0';
        
    when Astept_caracterul3 => 
        cod_introdus_intermediar(11 downto 8) <= iesire_numarator;
        data <= cod_introdus_intermediar;
        led_introdu <= '1';
        liber_ocupat <= '0';
        
    when Ocupat => 
        cod_verificare_intermediar <= (others => '0');
        data <= (others => '0');
        led_introdu <= '0';
        liber_ocupat <= '1';
        
    when Ocupat_Astept_caracterul1 => 
        cod_verificare_intermediar(3 downto 0) <= iesire_numarator;
        data <= cod_verificare_intermediar;
        led_introdu <= '1';
        liber_ocupat <= '1';
        
    when Ocupat_Astept_caracterul2 => 
        cod_verificare_intermediar(7 downto 4) <= iesire_numarator;
        data <= cod_verificare_intermediar;
        led_introdu <= '1';
        liber_ocupat <= '1';
        
    when Ocupat_Astept_caracterul3 => 
        cod_verificare_intermediar(11 downto 8) <= iesire_numarator;
        data <= cod_verificare_intermediar;
        led_introdu <= '1';
        liber_ocupat <= '1';
        
    when others =>
    
end case;
end if;
end process;

end Behavioral;
