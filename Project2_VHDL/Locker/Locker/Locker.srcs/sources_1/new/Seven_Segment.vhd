library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

entity Seven_Segment is
    Port ( signal clk : in std_logic;
           signal data : in std_logic_vector(11 downto 0);
           signal seg : out std_logic_vector(6 downto 0);
           signal an : out std_logic_vector(3 downto 0));
end Seven_Segment;

architecture Behavioral of Seven_Segment is

signal counter : std_logic_vector(16 downto 0);
signal selectie : std_logic_vector(1 downto 0);
signal hex : std_logic_vector(3 downto 0);
    
begin

process(clk)
begin
if rising_edge(clk) then
    counter <= counter + 1;
end if;
end process;

selectie <= counter(16 downto 15);

process(selectie, data)
begin
case selectie is
    when "00" => an <= "1110"; 
                 hex <= data(3 downto 0);
    when "01" => an <= "1101";
                 hex <= data(7 downto 4);
    when others => an <= "1011";
                   hex <= data(11 downto 8);
end case;
end process;

process(hex)
begin
case hex is
    when "0000" => seg <= "1000000"; -- 0
    when "0001" => seg <= "1111001"; -- 1
    when "0010" => seg <= "0100100"; -- 2
    when "0011" => seg <= "0110000"; -- 3
    when "0100" => seg <= "0011001"; -- 4
    when "0101" => seg <= "0010010"; -- 5
    when "0110" => seg <= "0000010"; -- 6
    when "0111" => seg <= "1111000"; -- 7
    when "1000" => seg <= "0000000"; -- 8
    when "1001" => seg <= "0010000"; -- 9
    when "1010" => seg <= "0001000"; -- A
    when "1011" => seg <= "0000011"; -- b
    when "1100" => seg <= "1000110"; -- C
    when "1101" => seg <= "0100001"; -- d
    when "1110" => seg <= "0000110"; -- E
    when others => seg <= "0001110"; -- F
end case;
end process;

end Behavioral;
